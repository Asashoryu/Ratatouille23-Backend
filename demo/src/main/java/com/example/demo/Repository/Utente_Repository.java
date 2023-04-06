package com.example.demo.Repository;

import com.example.demo.Model.Utente;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface Utente_Repository extends CrudRepository<Utente,String> {

    @Query(value = "select * from utente where utente.username= :username and utente.password = :password", nativeQuery = true)
    public Optional<Utente> login(@Param("username") String username, @Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "insert into utente (username, password, nome, cognome, ruolo, is_reimpostata) values (:username, :password, :nome, :cognome, :ruolo, :isReimpostata)", nativeQuery = true)
    public void crea(@Param("username") String username, @Param("password") String password, @Param("nome") String nome, @Param("cognome") String cognome, @Param("ruolo") String ruolo, @Param("isReimpostata") boolean isReimpostata);

    @Modifying
    @Transactional
    @Query(value = "UPDATE utente SET password = :nuova_password, is_reimpostata = true WHERE username = :username", nativeQuery = true)
    public void cambiaPassword(@Param("username") String username, @Param("nuova_password") String nuova_password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE utente SET token = :token WHERE username = :username", nativeQuery = true)
    public void setToken(@Param("username") String username, @Param("token") String token);

    @Query(value = "SELECT * FROM utente WHERE utente.ruolo IN ('AMMINISTRATORE', 'SUPERVISORE')", nativeQuery = true)
    Optional<List<Utente>> findAllAmministratoriESupervisori();

}

