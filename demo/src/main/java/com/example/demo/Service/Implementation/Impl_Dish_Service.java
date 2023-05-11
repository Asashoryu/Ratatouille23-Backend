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
    public boolean save(Dish dish) {
        try {
            dish_repository.save(dish);
            return true; // save operation successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // save operation failed
        }
    }

    @Override
    public boolean insert(Dish dish) {
        try {
            dish_repository.insertDish(dish.getName(), dish.getPrice(), dish.getCategory(), dish.getAllergy(), dish.isOrdinabile(), dish.getDescription());
            return true; // insert operation successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // insert operation failed
        }

    }

    @Override
    public boolean delete(Dish dish) {
        try {
            dish_repository.delete(dish);
            return true;
        } catch (Exception e) {
            System.err.println("Non è stato possibile eliminare questo piatto");
            return false;
        }
    }
}
