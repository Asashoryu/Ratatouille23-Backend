package com.example.demo.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dipendente")
public class Utente {
    @Id
    @Column(name="username")
    private String username;
    @Column(name="nome")
    private String nome;

    @Column(name="cognome")
    private String cognome;

    @Column(name="password")
    private String password;
    @Column(name="ruolo")
    private String ruolo;
    @Column(name="isReimpostata")
    private Boolean isReimpostata;
    // alt + ins Constructor + select none

    public Utente() {
    }
    // alt + ins Constructor + todos

    public Utente(String username, String nome, String cognome, String password, String ruolo, boolean isReimpostata) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
        this.isReimpostata = isReimpostata;
    }


    // alt ins + getter setter todos


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public Boolean isIsReimpostata() {
        return isReimpostata;
    }

    public void setIsReimpostata(Boolean isReimpostata) {
        this.isReimpostata = isReimpostata;
    }
}
