package com.example.demo.Service.Implementation;

import com.example.demo.Model.Ingridient;
import com.example.demo.Repository.Ingridient_Repository;
import com.example.demo.Service.Interface.I_Ingridient_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service("Impl_Ingridient_Service")
public class Impl_Ingridient_Service implements I_Ingridient_Service {
    @Autowired
    private Ingridient_Repository ingridient_repository;


    @Override
    public Optional<List<Ingridient>> get_all_ingridients() {
        return ingridient_repository.get_all_ingridients();
    }

    @Override
    public void deleteById(String id) {
        ingridient_repository.deleteById(id);
    }

    @Override
    public Optional<Ingridient> findById(String id) {
        return ingridient_repository.findById(id);
    }

    @Override
    public boolean insert(Ingridient ingridient) {
        try {
            ingridient_repository.insertIngrediente(ingridient.getName(), ingridient.getDescription(), ingridient.getMisura(), ingridient.getPrice(), ingridient.getQuantity(), ingridient.getSoglia(), ingridient.getTolleranza());
            return true; // insert operation successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // insert operation failed
        }
    }

    @Override
    public boolean save(Ingridient ingridient) {
        try {
            ingridient_repository.save(ingridient);
            return true; // save operation successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // save operation failed
        }
    }

}
