package com.example.demo.Controller;


import com.example.demo.DTO.Make_Dish_DTO;
import com.example.demo.Model.Dish;
import com.example.demo.Model.Ingridient;
import com.example.demo.Model.Make_Dish;
import com.example.demo.Service.Implementation.Impl_Dish_Service;
import com.example.demo.Service.Implementation.Impl_Ingridient_Service;
import com.example.demo.Service.Interface.I_Make_Dish_Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Make_Dish")
public class Make_Dish_Controller {
    @Autowired
    @Qualifier("Impl_Make_Dish_Service")
    private I_Make_Dish_Service i_make_dish_service;

    @Autowired
    @Qualifier("Impl_Dish_Service")
    private Impl_Dish_Service i_dish_service;

    @Autowired
    @Qualifier("Impl_Ingridient_Service")
    private Impl_Ingridient_Service i_ingridient_service;

    @Autowired
    private ModelMapper modelMapper;

    private Make_Dish_DTO convertDto(Make_Dish make_dish){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Make_Dish_DTO make_dish_dto = new Make_Dish_DTO();

        make_dish_dto=modelMapper.map(make_dish,Make_Dish_DTO.class);
        return make_dish_dto;
    }
    private  Make_Dish convertEntity(Make_Dish_DTO make_dish_dto){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Make_Dish make_dish = new Make_Dish();
        make_dish=modelMapper.map(make_dish_dto,Make_Dish.class);
        return make_dish;
    }

    @GetMapping("/get_ingridients_from/{dish}")
    public List<Make_Dish_DTO> get_ingridients_for_dish(@PathVariable String dish){
        Optional<List<Make_Dish>> make_dishes = i_make_dish_service.get_ingridients_from_dish(dish);
        if(make_dishes.isPresent()){
            List<Make_Dish_DTO> make_dish_dtos = new ArrayList<>();
            for(Make_Dish make:make_dishes.get()) make_dish_dtos.add(convertDto(make));
            return make_dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Non ci sono ingredienti per il piatto selezioanto");
    }
    @GetMapping("/get_dishes_for_/{ingridient}")
    public List<Make_Dish_DTO> get_dishes_for_ingridient(@PathVariable String ingridient){
        Optional<List<Make_Dish>> make_dishes = i_make_dish_service.get_dishes_from_ingridient(ingridient);
        if(make_dishes.isPresent()){
            List<Make_Dish_DTO> make_dish_dtos = new ArrayList<>();
            for(Make_Dish make:make_dishes.get()) make_dish_dtos.add(convertDto(make));
            return make_dish_dtos;
        }
        else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Non ci sono piatto per il ingrediente selezioanto");
    }

    @GetMapping("/get_all_make_dishes")
    public List<Make_Dish_DTO> findAll() {
        List<Make_Dish> makeDishes = (List<Make_Dish>) i_make_dish_service.findAll();
        if (!makeDishes.isEmpty()) {
            List<Make_Dish_DTO> makeDishDtos = new ArrayList<>();
            for (Make_Dish makeDish : makeDishes) {
                Make_Dish_DTO makeDishDto = new Make_Dish_DTO(
                        makeDish.getId(),
                        makeDish.getQuantity(),
                        makeDish.getDish().getName(),
                        makeDish.getIngridient().getName()
                );
                makeDishDtos.add(makeDishDto);
            }
            return makeDishDtos;
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Errore: Piatti non presenti");
        }
    }

    @PostMapping("/associa/{quantity}/{ingridientName}/{dishName}")
    public ResponseEntity<?> addMakeDish(@PathVariable float quantity,
                                         @PathVariable String ingridientName,
                                         @PathVariable String dishName) {
        // Find the related entities using their names
        Optional<Ingridient> ingridient = i_ingridient_service.findById(ingridientName);
        Optional<Dish> dish = i_dish_service.findById(dishName);

        // Create a new Make_Dish instance with the provided quantity and related entities
        Make_Dish makeDish = new Make_Dish();
        makeDish.setQuantity(quantity);
        makeDish.setDish(dish.get());
        makeDish.setIngridient(ingridient.get());

        // Save the new Make_Dish instance
        Optional<Make_Dish> newMakeDish = i_make_dish_service.save(makeDish);
        return ResponseEntity.ok(newMakeDish);
    }


    @DeleteMapping("/delete/{ingridientName}/{dishName}")
    public ResponseEntity<?> deleteMakeDish(@PathVariable String ingridientName,
                                            @PathVariable String dishName) {
        // Find the existing entity using its related entities
        Optional<Ingridient> ingridient = i_ingridient_service.findById(ingridientName);
        Optional<Dish> dish = i_dish_service.findById(dishName);
        Make_Dish existingMakeDish = i_make_dish_service.findByDishAndIngridient(dish.get().getName(), ingridient.get().getName());
        if (existingMakeDish != null) {
            i_make_dish_service.delete(existingMakeDish);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
