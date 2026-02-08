package com.example.B2BHotelBookingSystem.controllers;


import com.example.B2BHotelBookingSystem.dtos.Agency.AgencyResponse;
import com.example.B2BHotelBookingSystem.dtos.Agency.CreateAgencyRequest;
import com.example.B2BHotelBookingSystem.dtos.Agency.UpdateAgencyRequest;
import com.example.B2BHotelBookingSystem.services.AgencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String showCreateForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getPrincipal().getClass());
        System.out.println(auth.getPrincipal());

        model.addAttribute("agency",
                new CreateAgencyRequest(null, null, null, null));
        return "agencies/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createAgency(
            @Valid @ModelAttribute("agency") CreateAgencyRequest request,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
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
        if (city.isBlank()) {
            model.addAttribute("agencies",
                    service.findAllPaginated(PageRequest.of(page, size)));
        } else {
            model.addAttribute("agencies",
                    service.findAllPaginatedByCity(city, PageRequest.of(page, size)));
        }
        return "agencies/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public String findAgency(@PathVariable @Valid Long id, Model model) {
            model.addAttribute("agency",
                    service.findAgency(id));
        return "agencies/detail";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENCY')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        AgencyResponse agency = service.findAgency(id);

        model.addAttribute("agency",
                new UpdateAgencyRequest(
                        agency.id(),
                        agency.name(),
                        agency.address(),
                        agency.cityName(),
                        agency.tel()
                )
        );
        return "agencies/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENCY')")
    @PostMapping("/update")
    public String updateAgency(
            @Valid @ModelAttribute("agency") UpdateAgencyRequest request,
            BindingResult result
    ) {
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
