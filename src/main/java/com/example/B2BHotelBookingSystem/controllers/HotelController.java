package com.example.B2BHotelBookingSystem.controllers;


import com.example.B2BHotelBookingSystem.dtos.HotelCreateDTO;
import com.example.B2BHotelBookingSystem.dtos.HotelDTO;
import com.example.B2BHotelBookingSystem.dtos.HotelUpdateDTO;
import com.example.demo.services.HotelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/hotels")
public class HotelController extends BaseController{

    private final HotelService service;

    public HotelController(HotelService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAll(){
        return ResponseEntity.ok(service.getAllHotels());
    }

    @PostMapping
    public ResponseEntity<HotelDTO> create(@Valid @RequestBody HotelCreateDTO hotel){
        return new ResponseEntity<>(service.createHotel(hotel), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@NotNull @Positive @PathVariable Long id){
        service.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> update(@NotNull @Positive @PathVariable Long id, @Valid @RequestBody HotelUpdateDTO hotel){
        return ResponseEntity.ok(service.updateHotel(id, hotel));
    }


}
