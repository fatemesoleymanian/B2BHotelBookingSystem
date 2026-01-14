package com.example.B2BHotelBookingSystem.controllers;


import com.example.B2BHotelBookingSystem.dtos.Hotel.CreateHotelRequest;
import com.example.B2BHotelBookingSystem.dtos.Hotel.UpdateHotelRequest;
import com.example.B2BHotelBookingSystem.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController extends BaseController{

    private final HotelService service;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("hotelRequest",
                new CreateHotelRequest("", "", "", 1, ""));
        return "hotels/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createHotel(@Valid @ModelAttribute("hotelRequest") CreateHotelRequest request,
                              BindingResult result, Model model){
        if (result.hasErrors()) {
            return "hotels/create";
        }
        service.createHotel(request);
        return "redirect:/hotels";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listHotels(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "20") int size,
         @RequestParam(defaultValue = "") String city,
         Model model
    ) {
        if (city.isEmpty()) {
            model.addAttribute("hotelsPage", service.findAllPaginated(PageRequest.of(page, size)));
        }else {
            //Show hotels by city
            model.addAttribute("hotelsPage", service.findAllPaginatedByCity(city, PageRequest.of(page, size)));
        }
        return "hotels/list";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var hotel = service.findHotel(id);
        UpdateHotelRequest request = new UpdateHotelRequest(
                hotel.id(), hotel.name(),hotel.address(), hotel.cityName(),
                hotel.star(), hotel.tel());

        model.addAttribute("hotelRequest", request);
        return "hotels/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @PostMapping("/update")
    public String updateHotel(@Valid @ModelAttribute("hotelRequest") UpdateHotelRequest request,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "hotels/edit";
        }
        service.updateHotel(request);
        return "redirect:/hotels";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteHotel(@PathVariable Long id) {
        service.deleteHotel(id);
        return "redirect:/hotels";
    }
}
