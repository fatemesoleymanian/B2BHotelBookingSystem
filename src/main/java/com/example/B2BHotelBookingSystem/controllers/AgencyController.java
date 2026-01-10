package com.example.B2BHotelBookingSystem.controllers;


import com.example.B2BHotelBookingSystem.dtos.CreateAgencyRequest;
import com.example.B2BHotelBookingSystem.dtos.UpdateAgencyRequest;
import com.example.B2BHotelBookingSystem.services.AgencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/agencies")
@RequiredArgsConstructor
public class AgencyController extends BaseController{

    private final AgencyService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("agencyRequest",
                new CreateAgencyRequest("", "", "", ""));
        return "agencies/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createAgency(@Valid @ModelAttribute("agencyRequest") CreateAgencyRequest request,
                              BindingResult result, Model model){
        if (result.hasErrors()) {
            return "agencies/create";
        }
        service.createAgency(request);
        return "redirect:/agencies";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listAgencies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "") String city,
            Model model
    ) {
        if (city.isEmpty()) {
            model.addAttribute("agenciesPage", service.findAllPaginated(PageRequest.of(page, size)));
        }else {
            //Show agencies by city
            model.addAttribute("agenciesPage", service.findAllPaginatedByCity(city, PageRequest.of(page, size)));
        }
        return "agencies/list";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENCY')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var Agency = service.findAgency(id);
        UpdateAgencyRequest request = new UpdateAgencyRequest(
                Agency.id(), Agency.name(),Agency.address(), Agency.cityName(), Agency.tel());

        model.addAttribute("agencyRequest", request);
        return "agencies/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENCY')")
    @PostMapping("/update")
    public String updateAgency(@Valid @ModelAttribute("AgencyRequest") UpdateAgencyRequest request,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "agencies/edit";
        }
        service.updateAgency(request);
        return "redirect:/agencies";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteAgency(@PathVariable Long id) {
        service.deleteAgency(id);
        return "redirect:/agencies";
    }

}
