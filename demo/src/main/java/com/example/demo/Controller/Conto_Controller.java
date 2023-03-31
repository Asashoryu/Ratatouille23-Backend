package com.example.demo.Controller;


import com.example.demo.DTO.Conto_DTO;
import com.example.demo.DTO.Ordered_Dish_DTO;
import com.example.demo.Model.Conto;
import com.example.demo.Model.Dish;
import com.example.demo.Model.Ordered_Dish;
import com.example.demo.Model.Tavolo;
import com.example.demo.Repository.Conto_Repository;
import com.example.demo.Service.Interface.I_Conto_Service;
import com.example.demo.Service.Interface.I_Dish_Service;
import com.example.demo.Service.Interface.I_Ordered_Dish_Service;
import com.example.demo.Service.Interface.I_Tavolo_Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Conto")
public class Conto_Controller {
    @Autowired
    @Qualifier("Impl_Conto_Service")
    private I_Conto_Service i_conto_service;

    @Autowired
    @Qualifier("Impl_Tavolo_Service")
    private I_Tavolo_Service i_tavolo_service;

    @Autowired
    @Qualifier("Impl_Ordered_Dish_Service")
    private I_Ordered_Dish_Service i_ordered_dish_service;

    @Autowired
    @Qualifier("Impl_Dish_Service")
    private I_Dish_Service  i_dish_service;

    @Autowired
    private Conto_Repository contoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Conto_DTO convertDto(Conto conto){
        Conto_DTO conto_dto = new Conto_DTO();
        conto_dto.setId(conto.getId());
        conto_dto.setTime(conto.getTime());
        conto_dto.setTotal(conto.getTotal());
        conto_dto.setIs_chiuso(conto.isIs_chiuso());
        conto_dto.setTavoloId(conto.getTavolo().getId());
        return conto_dto;
    }

    private Conto convertEntity(Conto_DTO conto_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Conto conto = new Conto();
        conto = modelMapper.map(conto_dto,Conto.class);
        return conto;
    }
    @GetMapping("/get_all_checks")
    public List<Conto_DTO> get_all() {
        Optional<List<Conto>> contos = i_conto_service.get_all();
        if (contos.isPresent()) {
            List<Conto_DTO> conto_dtos = new ArrayList<>();
            for (Conto conto : contos.get()) {
                Conto_DTO conto_dto = new Conto_DTO(
                        conto.getId(),
                        conto.getTime(),
                        conto.getTotal(),
                        conto.isIs_chiuso(),
                        conto.getTavolo().getId()
                );
                conto_dtos.add(conto_dto);
            }
            return conto_dtos;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti");
        }
    }

    @GetMapping("/get_all_checks_for_table:{tavolo}")
    public List<Conto_DTO> get_all_for_table(@PathVariable int tavolo){
        Optional<List<Conto>> contos = i_conto_service.get_all_check_for_table(tavolo);
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos=new ArrayList<>();
            for(Conto conto:contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti per tavolo selezionato");
    }


    @GetMapping("/get_all_checks_form:{min}_to:{max}")
    public List<Conto_DTO> get_checks_in_interval(@PathVariable int min,@PathVariable int max){
        Optional<List<Conto>> contos = i_conto_service.get_all_check_in_interval(min,max);
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos = new ArrayList<>();
            for(Conto conto:contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti per intervallo selezionato");
    }
    @GetMapping("/get_all_from:{min}_to:{max}_for:{tavolo}")
    public List<Conto_DTO> get_checks_in_interval_for_table(@PathVariable int min,@PathVariable int max, @PathVariable int tavolo){
        Optional<List<Conto>> contos = i_conto_service.get_all_check_for_table_in_interval(min, max, tavolo);
        if(contos.isPresent()){
            List<Conto_DTO> conto_dtos = new ArrayList<>();
            for(Conto conto : contos.get()) conto_dtos.add(convertDto(conto));
            return conto_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Conti non presenti per tavolo selezionato nel intervallo selzionato");
    }

    @DeleteMapping("/delete_conto:{id}")
    @Transactional
    public ResponseEntity<String> deleteConto(@PathVariable int id) {
        Optional<Conto> contoOptional = i_conto_service.findById(id);
        if (contoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conto not found");
        }
        Conto conto = contoOptional.get();
        i_conto_service.deleteById(id);
        return ResponseEntity.ok("Conto deleted successfully");
    }

    @PostMapping("/save_conto:{id}:{time}:{total}:{isChiuso}:{tavoloId}")
    @Transactional
    public ResponseEntity<String> saveConto(
            @PathVariable int id,
            @PathVariable int time,
            @PathVariable float total,
            @PathVariable boolean isChiuso,
            @PathVariable int tavoloId,
            @RequestBody List<Ordered_Dish_DTO> orderedDishes) {
        Optional<Tavolo> tavoloConto = i_tavolo_service.findById(tavoloId);

        if (tavoloConto.isPresent()) {

            Conto conto = new Conto();
            conto.setId(id);
            conto.setTime(time);
            conto.setTotal(total);
            conto.setIs_chiuso(isChiuso);
            conto.setTavolo(tavoloConto.get());

            List<Ordered_Dish> orderedDishEntities = new ArrayList<>();

            for (Ordered_Dish_DTO dishDTO : orderedDishes) {
                Optional<Dish> dishOptional = i_dish_service.findById(dishDTO.getDishName());
                if (dishOptional.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Piatto non trovato");
                }
                Dish dish = dishOptional.get();
                Ordered_Dish orderedDish = new Ordered_Dish(dishDTO.getQuantity(), conto, dish);
                orderedDishEntities.add(orderedDish);
            }

            conto.setOrdered_dishes(orderedDishEntities);

            i_conto_service.save(conto);
            i_ordered_dish_service.saveAll(orderedDishEntities);

            return ResponseEntity.ok("Conto saved successfully");
        }

        return ResponseEntity.internalServerError().build();
    }

    @PutMapping("/update:{id}:{isChiuso}")
    public ResponseEntity<Conto> updateContoIsChiusoById(@PathVariable int id, @PathVariable boolean isChiuso) {
        Optional<Conto> contoOptional = contoRepository.findById(id);
        if (contoOptional.isPresent()) {
            Conto conto = contoOptional.get();
            conto.setIs_chiuso(isChiuso);
            contoRepository.save(conto);
            return ResponseEntity.ok(conto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/get_free_id")
    public int getFreeContoId() {
        int id = 1;
        while (i_conto_service.findById(id).isPresent()) {
            id++;
        }
        return id;
    }

}
