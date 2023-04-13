package com.example.demo.Service.Interface;


import com.example.demo.Model.Dish;

import java.util.List;
import java.util.Optional;

public interface I_Dish_Service {

    Optional<Dish> findById(String nome);

    Optional<List<Dish>> getAllDishes();

    Optional<List<Dish>> getCategoryDishes(String category);

    Optional<List<Dish>> getAvailableDishes();

    boolean save(Dish dish);

    void insert(String nome, String descrizione, String categoria, float prezzo, boolean ordinabile, String allergie);
}


