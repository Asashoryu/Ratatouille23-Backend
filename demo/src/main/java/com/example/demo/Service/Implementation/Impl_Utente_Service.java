package com.example.demo.Service.Implementation;


import com.example.demo.Model.Utente;
import com.example.demo.Repository.Utente_Repository;
import com.example.demo.Service.Interface.I_Utente_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service("Impl_Utente_Service")
public class Impl_Utente_Service implements I_Utente_Service {
    @Autowired // crea l'oggetto
    private Utente_Repository dipendente_repository;
    @Override
    public Optional<Utente> login(String username, String password){
        return dipendente_repository.login(username,password);
    }

    @Override
    public void crea(String username, String password, String nome, String cognome, String ruolo, Boolean isReimpostata) {
        dipendente_repository.crea(username, password, nome, cognome, ruolo, isReimpostata);
    }

    @Override
    public void cambiaPassword(String username, String nuovaPassword) {
        dipendente_repository.cambiaPassword(username, nuovaPassword);
    }

    @Override
    public void setToken(String username, String token) {
        dipendente_repository.setToken(username, token);
    }

    @Override
    public Optional<List<Utente>> findAllAmministratoriESupervisori() {
        return dipendente_repository.findAllAmministratoriESupervisori();
    }
}
