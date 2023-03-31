package com.example.demo.Service.Interface;

import com.example.demo.Model.Ordered_Dish;

import java.util.List;
import java.util.Optional;

public interface I_Ordered_Dish_Service {

    Optional<Ordered_Dish> findById(int id);

    Optional<List<Ordered_Dish>> getAllOrderedDishes();
    public Optional<List<Ordered_Dish>> get_dishes_by_check(int conto);
    public Optional<List<Ordered_Dish>> get_checks_by_dish(String piatto);

    void saveAll(List<Ordered_Dish> dishesToSave);

    void save(Ordered_Dish orderedDish);
}