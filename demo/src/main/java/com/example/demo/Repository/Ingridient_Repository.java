package com.example.demo.Repository;

import com.example.demo.Model.Ingridient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface Ingridient_Repository extends CrudRepository<Ingridient,String> {
    @Query(value = "select * from ingrediente",nativeQuery = true)
    public Optional<List<Ingridient>> get_all_ingridients();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO ingrediente (nome, description, misura, costo, quantita, soglia, tolleranza) VALUES (:nome, :description, :misura, :costo, :quantita, :soglia, :tolleranza)", nativeQuery = true)
    void insertIngrediente(@Param("nome") String nome, @Param("description") String description, @Param("misura") String misura, @Param("costo") float costo, @Param("quantita") float quantita, @Param("soglia") float soglia, @Param("tolleranza") float tolleranza);

}
