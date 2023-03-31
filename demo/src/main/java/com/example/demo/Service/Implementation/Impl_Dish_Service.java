package com.example.demo.Service.Implementation;

import com.example.demo.Model.Dish;
import com.example.demo.Repository.Dish_Repository;
import com.example.demo.Service.Interface.I_Dish_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("Impl_Dish_Service")
@Transactional(propagation = Propagation.REQUIRED)
public class Impl_Dish_Service implements I_Dish_Service {
    @Autowired
    private Dish_Repository dish_repository;

    @Override
    public Optional<Dish> findById(String nome) {
        return dish_repository.findById(nome);
    }

    @Override
    public Optional<List<Dish>> getAllDishes() {
        return dish_repository.getAllDishes();
    }

    @Override
    public Optional<List<Dish>> getCategoryDishes(String category) {
        return dish_repository.getCategoryDishes(category);
    }

    @Override
    public Optional<List<Dish>> getAvailableDishes() {
        return dish_repository.getAvailableDishes();
    }

    @Override
    public void insert(Dish dish) {
        dish_repository.save(dish);
    }

    @Override
    public void insert(String nome, String descrizione, String categoria, float prezzo, boolean ordinabile, String allergie) {
        Dish dish = new Dish();
        dish.setName(nome);
        dish.setDescription(descrizione);
        dish.setCategory(categoria);
        dish.setPrice(prezzo);
        dish.setAllergy(allergie);
        dish.setOrdinabile(ordinabile);

        dish_repository.save(dish);
    }
}
