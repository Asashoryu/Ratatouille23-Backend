package com.example.demo.Controller;

import com.example.demo.DTO.Dish_DTO;
import com.example.demo.Model.Dish;
import com.example.demo.Service.Interface.I_Dish_Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/Dish")
public class Dish_Controller {
    @Autowired
    @Qualifier("Impl_Dish_Service")
    private I_Dish_Service i_dish_service;

    @Autowired
    private ModelMapper modelMapper;

    private Dish convertEntity(Dish_DTO dish_dto){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Dish dish = new Dish();
        dish = modelMapper.map(dish_dto, Dish.class);

        return dish;
    }
    private Dish_DTO convertDto(Dish dish){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Dish_DTO dish_dto = new Dish_DTO();
        dish_dto = modelMapper.map(dish, Dish_DTO.class);

        return dish_dto;
    }

    @GetMapping("/getdishes")
    public List<Dish_DTO> get_all_dishes(){
        Optional<List<Dish>> dishes = i_dish_service.getAllDishes();
        if(dishes.isPresent()){
            List<Dish_DTO> dish_dtos = new ArrayList<>();
            for (Dish dish:dishes.get()) {
                Dish_DTO dishTemp = convertDto(dish);
                dish_dtos.add(dishTemp);
            }
            return dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Piatti non presenti");
    }

    @GetMapping("/get/{category}")
    public List<Dish_DTO> get_category_dishes(@PathVariable String category){
        Optional<List<Dish>> dishes=i_dish_service.getCategoryDishes(category);
        if(dishes.isPresent()){
            List<Dish_DTO> dish_dtos=new ArrayList<>();
            for (Dish dish:dishes.get()) dish_dtos.add(convertDto(dish));
            return dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Piatti non presenti per la categoria selezionata");
    }

    @PostMapping("/insert_piatto/{nome}/{categoria}/{prezzo}/{ordinabile}/{allergie}/{descrizione}")
    public ResponseEntity<String> insert_piatto(@PathVariable String nome,
                                                @PathVariable String categoria,
                                                @PathVariable float prezzo,
                                                @PathVariable Boolean ordinabile,
                                                @PathVariable(required = false) String allergie,
                                                @PathVariable(required = false) String descrizione) {
        if (allergie == null | allergie.equals("-")) {
            allergie = "";
        }
        if (descrizione == null | descrizione.equals("-")) {
            descrizione = "";
        }

        Dish dish = new Dish(nome, prezzo, categoria, allergie, ordinabile, descrizione);
        boolean success = i_dish_service.save(dish);
        if (success) {
            return ResponseEntity.ok("Piatto inserito con successo");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'inserimento del piatto");
        }
    }


    @PutMapping("/update_piatto/{nome}/{categoria}/{prezzo}/{ordinabile}/{allergie}/{descrizione}")
    public ResponseEntity<String> update_piatto(@PathVariable String nome,
                              @PathVariable String categoria,
                              @PathVariable float prezzo,
                              @PathVariable Boolean ordinabile,
                              @PathVariable String allergie,
                              @PathVariable String descrizione){

        if (allergie == null | allergie.equals("-")) {
            allergie = "";
        }
        if (descrizione == null | descrizione.equals("-")) {
            descrizione = "";
        }

        Optional<Dish> dish = i_dish_service.findById(nome);
        if (dish != null && dish.isPresent()) {
            dish.get().setName(nome);
            dish.get().setCategory(categoria);
            dish.get().setPrice(prezzo);
            dish.get().setOrdinabile(ordinabile);
            dish.get().setAllergy(allergie);
            dish.get().setDescription(descrizione);
            boolean success = i_dish_service.save(dish.get());
            if (success) {
                return ResponseEntity.ok("Piatto modificato con successo");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nella modifica del piatto");
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Piatto non trovato");
    }


}
