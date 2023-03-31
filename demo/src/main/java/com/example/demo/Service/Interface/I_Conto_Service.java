package com.example.demo.Service.Interface;
import com.example.demo.Model.Conto;

import java.util.List;
import java.util.Optional;

public interface I_Conto_Service {

    Optional<Conto> findById(int id);
    void deleteById(int id);

    Optional<Conto> save(Conto conto);

    public Optional<List<Conto>> get_all();
    public Optional<List<Conto>> get_all_check_for_table(int tavolo);
    public Optional<List<Conto>> get_all_check_in_interval(int minimo,int maximum);
    public Optional<List<Conto>> get_all_check_for_table_in_interval(int minimo,int maximum,int tavolo);

}
