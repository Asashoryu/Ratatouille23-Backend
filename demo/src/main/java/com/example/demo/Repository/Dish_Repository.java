package com.example.demo.Repository;
import com.example.demo.Model.Dish;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface Dish_Repository extends CrudRepository<Dish,String> {

    @Query(value = "SELECT * FROM piatto", nativeQuery = true)
    Optional<List<Dish>> getAllDishes();

    @Query(value="SELECT * FROM piatto WHERE piatto.categoria = :category", nativeQuery = true)
    Optional<List<Dish>> getCategoryDishes(@Param("category") String category);

    @Query(value="SELECT * FROM piatto WHERE piatto.allergie LIKE %:allergy%", nativeQuery = true)
    Optional<List<Dish>> getDishesByAllergy(@Param("allergy") String allergy);

    @Query(value = "SELECT * FROM piatto WHERE piatto.ordinabile = true", nativeQuery = true)
    Optional<List<Dish>> getAvailableDishes();

    @Query(value = "SELECT * FROM piatto WHERE piatto.nome = :name", nativeQuery = true)
    Optional<Dish> getDishByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE piatto SET prezzo = :price WHERE nome = :name", nativeQuery = true)
    void updateDishPrice(@Param("name") String name, @Param("price") float price);

    @Transactional
    @Modifying
    @Query(value = "UPDATE piatto SET ordinabile = :available WHERE nome = :name", nativeQuery = true)
    void updateDishAvailability(@Param("name") String name, @Param("available") boolean available);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO piatto (nome, prezzo, categoria, allergie, ordinabile, description) VALUES (:name, :price, :category, :allergy, :ordiniabile, :description)", nativeQuery = true)
    void insertDish(@Param("name") String name, @Param("price") float price, @Param("category") String category, @Param("allergy") String allergy, @Param("ordiniabile") boolean ordiniabile, @Param("description") String description);

}

