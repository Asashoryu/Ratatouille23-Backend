package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="piatto")
public class Dish {
    @Id
    @Column(name="nome")
    private String name;
    @Column(name="prezzo")
    private float price;
    @Column(name="categoria")
    private String category;
    @Column(name="allergie")
    private String allergy;
    @Column(name="ordinabile")
    private Boolean ordinabile;
    @Column(name="description")
    private String description;

    // uno a molti si crea la lista del molti
    @OneToMany(mappedBy = "dish" ,fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Ordered_Dish> ordered_dishes= new ArrayList<>();

    @OneToMany(mappedBy = "dish",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Make_Dish> made_dishes = new ArrayList<>();

    public Dish() {
    }

    public Dish(String name, float price, String category, String allergy, Boolean ordinabile, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.allergy = allergy;
        this.ordinabile = ordinabile;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean isOrdinabile() {
        return ordinabile;
    }

    public void setOrdinabile(Boolean ordinabile) {
        this.ordinabile = ordinabile;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public List<Ordered_Dish> getOrdered_dishes() {
        return ordered_dishes;
    }

    public void setOrdered_dishes(List<Ordered_Dish> ordered_dishes) {
        this.ordered_dishes = ordered_dishes;
    }

    public List<Make_Dish> getMade_dishes() {
        return made_dishes;
    }

    public void setMade_dishes(List<Make_Dish> made_dishes) {
        this.made_dishes = made_dishes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
