package com.example.demo.Model;


import jakarta.persistence.*;

@Entity
@Table(name="ingrediente")
public class Ingridient {
    @Id
    @Column(name="nome")
    private String name;
    @Column(name="costo")
    private float price;
    @Column (name="quantita")
    private float quantity;
    @Column (name="misura")
    private  String misura;

    @Column (name="soglia")
    private float soglia;

    @Column (name="tolleranza")
    private float tolleranza;
    @Column (name="description")
    private String description;

    public Ingridient() {

    }

    public Ingridient(String name, float price, float quantity, String misura, float soglia, float tolleranza, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.misura = misura;
        this.soglia = soglia;
        this.tolleranza = tolleranza;
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMisura() {
        return misura;
    }

    public void setMisura(String misura) {
        this.misura = misura;
    }

    public float getSoglia() {
        return soglia;
    }

    public void setSoglia(float soglia) {
        this.soglia = soglia;
    }

    public float getTolleranza() {
        return tolleranza;
    }

    public void setTolleranza(float tolleranza) {
        this.tolleranza = tolleranza;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
