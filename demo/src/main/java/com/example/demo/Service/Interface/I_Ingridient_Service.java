package com.example.demo.Service.Interface;


import com.example.demo.Model.Conto;
import com.example.demo.Model.Ingridient;

import java.util.List;
import java.util.Optional;

public interface I_Ingridient_Service {
    public Optional<List<Ingridient>> get_all_ingridients();

    void deleteById(String id);

    Optional<Ingridient> findById(String id);

    Optional<Ingridient> save(Ingridient ingridient);
}