package com.example.demo.Controller;

import com.example.demo.DTO.Ingridient_DTO;
import com.example.demo.Model.Ingridient;
import com.example.demo.Model.Utente;
import com.example.demo.Service.Interface.I_Ingridient_Service;
import com.example.demo.Service.Interface.I_Utente_Service;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.annotation.PreDestroy;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/Ingridient")
public class Ingridient_Controller {
    @Autowired
    @Qualifier("Impl_Ingridient_Service")
    private I_Ingridient_Service i_ingridient_service;

    @Autowired
    @Qualifier("Impl_Utente_Service")
    private I_Utente_Service i_utente_service;
    @Autowired
    ModelMapper modelMapper;

    ExecutorService executor = Executors.newSingleThreadExecutor();

    private Ingridient convertEntity(Ingridient_DTO ingridient_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Ingridient ingridient = new Ingridient();
        ingridient=modelMapper.map(ingridient_dto,Ingridient.class);
        return ingridient;
    }
    private  Ingridient_DTO convertDto(Ingridient ingridient){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Ingridient_DTO ingridient_dto= new Ingridient_DTO();
        ingridient_dto=modelMapper.map(ingridient,Ingridient_DTO.class);
        return ingridient_dto;
    }
    @GetMapping("/get_all_ingridients")
    public List<Ingridient_DTO> get_all(){
        Optional<List<Ingridient>> ingridients= i_ingridient_service.get_all_ingridients();
        if (ingridients.isPresent()) {
            List<Ingridient_DTO> ingridient_dtos= new ArrayList<>();
            for(Ingridient ingridient:ingridients.get()) ingridient_dtos.add(convertDto(ingridient));
            return ingridient_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Ingredienti non presenti");
    }

    @PostMapping("/insert_ingridient/{name}/{price}/{quantity}/{misura}/{soglia}/{tolleranza}/{description}")
    public ResponseEntity<String> insert_ingridient(@PathVariable String name,
                                @PathVariable float price,
                                @PathVariable float quantity,
                                @PathVariable String misura,
                                @PathVariable float soglia,
                                @PathVariable float tolleranza,
                                @PathVariable String description){

        if (description == null | description.equals("-")) {
            description = "";
        }

        Ingridient ingridient = new Ingridient(name, price, quantity, misura, soglia, tolleranza, description);
        boolean success = i_ingridient_service.insert(ingridient);
        if (success) {
            return ResponseEntity.ok("Ingrediente salvato con successo");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nel salvataggio dell'ingrediente");
        }
    }

    @PutMapping("/update_ingredient/{name}/{price}/{quantity}/{misura}/{soglia}/{tolleranza}/{description}")
    public ResponseEntity<String> updateIngredient(@PathVariable String name,
                                 @PathVariable float price,
                                 @PathVariable float quantity,
                                 @PathVariable String misura,
                                 @PathVariable float soglia,
                                 @PathVariable float tolleranza,
                                 @PathVariable String description) {

        if (description == null | description.equals("-")) {
            description = "";
        }

        Optional<Ingridient> ingredient = i_ingridient_service.findById(name);
        if (ingredient.isPresent()) {
            ingredient.get().setPrice(price);
            ingredient.get().setQuantity(quantity);
            ingredient.get().setMisura(misura);
            ingredient.get().setSoglia(soglia);
            ingredient.get().setTolleranza(tolleranza);
            ingredient.get().setDescription(description);
            boolean success = i_ingridient_service.save(ingredient.get());
            if (success) {
                return ResponseEntity.ok("Ingrediente salvato con successo");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nel salvataggio dell'ingrediente");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient non trovato");
        }
    }


    @PutMapping("/updateQuantity/{name}/{quantity}")
    public ResponseEntity<Ingridient> updateIngredientQuantityByName(@PathVariable String name,
                                                                     @PathVariable float quantity) {
        Optional<Ingridient> optionalIngredient = i_ingridient_service.findById(name);
        if (optionalIngredient.isPresent()) {
            Ingridient ingredient = optionalIngredient.get();
            ingredient.setQuantity(quantity);
            i_ingridient_service.insert(ingredient);

            // Check if the updated quantity is below the soglia value
            if (ingredient.getQuantity() < ingredient.getSoglia()) {
                // Get the Firebase token of the user who created the ingredient
                Optional<List<Utente>> utenti = i_utente_service.findAllAmministratoriESupervisori();
                if (utenti.isPresent()) {
                    for (Utente utente : utenti.get()) {
                        String token = utente.getToken();
                        if (token != null) {
                            executor.submit(() -> sendNotification(token, utente.getNome(), ingredient.getName()));
                        }
                    }
                }
            }
            return ResponseEntity.ok(ingredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void sendNotification(String token, String nomeUtente, String nomeIngrediente) {
        String message = String.format("Ciao " + nomeUtente + ", la quantità dell'ingrediente " + nomeIngrediente + " è scesa sotto la soglia stabilita");

        Message fcmMessage = Message.builder()
                .setNotification(Notification.builder()
                        .setTitle("Attenzione, quantità ingrediente insufficiente")
                        .setBody(message)
                        .build())
                .setToken(token)
                .build();

        try {
            FirebaseMessaging.getInstance().send(fcmMessage);
        } catch (FirebaseMessagingException e) {
            System.err.println("Impossibile inviare la notifica al client");
            e.printStackTrace();
        }
    }

    // Shutdown the executor when the application is shutting down
    @PreDestroy
    public void onDestroy() {
        executor.shutdown();
    }



    @DeleteMapping("/delete_ingridient/{id}")
    public void delete_ingridient(@PathVariable String id){
        i_ingridient_service.deleteById(id);
    }
}
