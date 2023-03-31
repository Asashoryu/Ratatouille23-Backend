package com.example.demo.Service.Implementation;

import com.example.demo.Model.Make_Dish;
import com.example.demo.Repository.Make_Dish_Repository;
import com.example.demo.Service.Interface.I_Make_Dish_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("Impl_Make_Dish_Service")
public class Impl_Make_Dish_Service implements I_Make_Dish_Service {
    @Autowired
    private Make_Dish_Repository make_dish_repository;

    @Override
    public Optional<List<Make_Dish>> get_ingridients_from_dish(String piatto) {
        return make_dish_repository.get_ingridients_from_dish(piatto);
    }

    @Override
    public Optional<List<Make_Dish>> get_dishes_from_ingridient(String ingrediente) {
        return make_dish_repository.get_dishes_from_ingridient(ingrediente);
    }

    @Override
    public Iterable<Make_Dish> findAll() {
        return make_dish_repository.findAll();
    }

    @Override
    public Optional<Make_Dish> save(Make_Dish makeDish) {
        return Optional.of(make_dish_repository.save(makeDish));
    }

    @Override
    public void delete(Make_Dish makeDish) {
        make_dish_repository.delete(makeDish);
    }

    @Override
    public Make_Dish findByDishAndIngridient(String dish, String ingridient) {
        return make_dish_repository.findByDishAndIngridient(dish, ingridient);
    }
}
