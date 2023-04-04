package com.example.demo.Service.Interface;

import com.example.demo.Model.Utente;

import java.util.Optional;

public interface I_Utente_Service {
    public Optional<Utente> login(String username, String password);

    void crea(String username, String password, String nome, String cognome, String ruolo, Boolean isReimpostata);

    void cambiaPassword(String username, String nuovaPassword);

    void setToken(String username, String token);

}