package com.example.B2BHotelBookingSystem.controllers;

import com.example.B2BHotelBookingSystem.dtos.Reserve.CreateReserveRequest;
import com.example.B2BHotelBookingSystem.dtos.Reserve.UpdateReserveRequest;
import com.example.B2BHotelBookingSystem.services.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reserves")
public class ReserveController extends BaseController{

    private final ReservationService service;

    @PreAuthorize("hasRole('AGENCY')")
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("reserveRequest",
                new CreateReserveRequest(null,null, null, null, BigDecimal.ZERO, null,null
                ,"","","","",null));
        return "reserves/create";
    }

    @PreAuthorize("hasRole('AGENCY')")
    @PostMapping("/create")
    public String createReserve(@Valid @ModelAttribute("reserveRequest") CreateReserveRequest request,
                              BindingResult result, Model model){
        if (result.hasErrors()) {
            return "reserves/create";
        }
        service.createReserve(request);
        return "redirect:/reserves";
    }
    //we need create reserve from hotel access too
    @PreAuthorize("hasRole('HOTEL')")
    @GetMapping("/hotel/create")
    public String showCreateFormForHotel(Model model){
        model.addAttribute("reserveRequest",
                new CreateReserveRequest(null,null, null, null, BigDecimal.ZERO, null,null
                        ,"","","","",null));
        return "";
    }

    @PreAuthorize("hasRole('HOTEL')")
    @PostMapping("/hotel/create")
    public String createReserveForHotel(@Valid @ModelAttribute("reserveRequest") CreateReserveRequest request,
                             BindingResult result, Model model){

        return "";
    }

    //should be done
    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @GetMapping
    public String listReseves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") long hotelId,
            @RequestParam(defaultValue = "0") long agencyId,
            @RequestParam(defaultValue = "") String status,
            Model model
    ) {
        return "reserves/list";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var reserve = service.findReserve(id);
        UpdateReserveRequest request = new UpdateReserveRequest(
                reserve.id(),reserve.status().name(),reserve.guestFirstName(),reserve.guestLastName(),reserve.guestPhoneNumber(),reserve.description());

        model.addAttribute("reserveRequest", request);
        return "reserves/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @PostMapping("/update")
    public String updateReserve(@Valid @ModelAttribute("reserveRequest") UpdateReserveRequest request,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "reserves/edit";
        }
        service.updateReserve(request);
        return "redirect:/reserves";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @PostMapping("/update_status")
    public String updateReserveStatus(@Valid @ModelAttribute("reserveRequest") UpdateReserveRequest request,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "reserves/edit";
        }
        service.updateReserveStatus(request);
        return "redirect:/reserves";
    }

}
