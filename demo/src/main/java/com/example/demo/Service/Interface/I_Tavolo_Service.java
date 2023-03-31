package com.example.demo.Service.Interface;


import com.example.demo.Model.Tavolo;

import java.util.List;
import java.util.Optional;
public interface I_Tavolo_Service {

    Optional<Tavolo> findById(int id);

    public Optional<List<Tavolo>> get_all_tables();
    public Optional<List<Tavolo>> get_free_table();
    public Optional<Tavolo> get_specific_table(int id);

    Optional<Tavolo> save(Tavolo tavolo);

    void deleteById(int id);
}
