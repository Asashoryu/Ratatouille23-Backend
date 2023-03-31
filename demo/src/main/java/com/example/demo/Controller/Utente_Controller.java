package com.example.demo.Controller;

import com.example.demo.Model.Utente;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.DTO.Utente_DTO;
import com.example.demo.Service.Interface.I_Utente_Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/Utente") //ogni API Rest inzia con Utente cosi non deov scrivelo io ogni volta
public class Utente_Controller {
    @Autowired
    @Qualifier("Impl_Utente_Service")
    private I_Utente_Service i_dipendente_service;

    @Autowired
    private ModelMapper modelMapper;
    private Utente convertEntity(Utente_DTO dipendente_dto){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Utente dipendente = new Utente();
        dipendente = modelMapper.map(dipendente_dto, Utente.class);

        return dipendente;
    }
    private Utente_DTO convertDto(Utente dipendente){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Utente_DTO dipendente_dto = new Utente_DTO();
        dipendente_dto = modelMapper.map(dipendente, Utente_DTO.class);

        return dipendente_dto;
    }
    @GetMapping("/login:{username}:{password}")
    public Utente_DTO log_in(@PathVariable String username, @PathVariable String password){
        Optional<Utente> utente= i_dipendente_service.login(username,password);
        if(utente.isPresent()) {
            Utente_DTO dipendente_dto =convertDto(utente.get());
            return dipendente_dto;

        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: user name o password errata");
    }

    @PostMapping("/crea:{username}:{password}:{nome}:{cognome}:{ruolo}:{isReimpostata}")
    public void crea(@PathVariable String username, @PathVariable String password,
                     @PathVariable String nome, @PathVariable String cognome,
                     @PathVariable String ruolo, @PathVariable String isReimpostata) {
        try {
            i_dipendente_service.crea(username, password, nome, cognome, ruolo, Boolean.valueOf(isReimpostata));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore nella creazione del dipendente", e);
        }
    }

    @PutMapping("/cambiaPassword:{username}:{nuovaPassword}")
    public ResponseEntity<String> cambiaPassword(@PathVariable String username, @PathVariable String nuovaPassword) {
        try {
            i_dipendente_service.cambiaPassword(username, nuovaPassword);
            return ResponseEntity.ok("Password cambiata con successo");

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Errore nel cambiamento della password", e);
        }
    }

}

