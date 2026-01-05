package com.example.B2BHotelBookingSystem.controllers;

import com.example.B2BHotelBookingSystem.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController extends BaseController{

    private final ContractRepository repository;

    public ContractController(ContractRepository repository){
        this.repository = repository;
    }
}
