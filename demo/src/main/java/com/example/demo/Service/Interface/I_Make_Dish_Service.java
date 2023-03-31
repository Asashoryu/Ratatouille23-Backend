package com.example.demo.Service.Interface;

import com.example.demo.Model.Make_Dish;

import java.util.List;
import java.util.Optional;

public interface I_Make_Dish_Service {
    public Optional<List<Make_Dish>> get_ingridients_from_dish(String piatto);

    public Optional<List<Make_Dish>> get_dishes_from_ingridient(String ingrediente);

    public Iterable<Make_Dish> findAll();

    Optional<Make_Dish> save(Make_Dish makeDish);

    void delete(Make_Dish makeDish);

    Make_Dish findByDishAndIngridient(String dish, String ingridient);
}
