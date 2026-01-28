package com.example.B2BHotelBookingSystem.controllers;


import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.dtos.Hotel.Rate.CreateRateRequest;
import com.example.B2BHotelBookingSystem.services.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rates")
public class RateController extends BaseController{

    private final RateService service;
    //i'm not sure what methods we need

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("rateRequest",
                new CreateRateRequest("",null,null, LocalDateTime.now(), LocalDateTime.now(), BigDecimal.ZERO,0));
        return "rates/create";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @PostMapping("/create")
    public String createRate(@Valid @ModelAttribute("rateRequest") CreateRateRequest request,
                             BindingResult result, Model model){
        if (result.hasErrors()) {
            return "rates/create";
        }
        service.createRate(request);
        return "redirect:/rates";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @GetMapping
    public String listRates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") long agencyId,
            @RequestParam(defaultValue = "0") long hotelId,
            @RequestParam(defaultValue = "") LocalDateTime from,
            @RequestParam(defaultValue = "") LocalDateTime to,
            Model model
    ) {

        return "rates/list";
    }

}
