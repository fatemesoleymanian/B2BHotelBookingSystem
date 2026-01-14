package com.example.B2BHotelBookingSystem.controllers;


import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.dtos.Availablity.CreateInventoryRequest;
import com.example.B2BHotelBookingSystem.dtos.Availablity.UpdateInventoryRequest;
import com.example.B2BHotelBookingSystem.services.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inventories")
public class InventoryController extends BaseController{

    private final InventoryService service;

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("inventoryRequest",
                new CreateInventoryRequest(null, LocalDateTime.now(), LocalDateTime.now(), 0));
        return "inventories/create";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @PostMapping("/create")
    public String createInventory(@Valid @ModelAttribute("inventoryRequest") CreateInventoryRequest request,
                             BindingResult result, Model model){
        if (result.hasErrors()) {
            return "inventories/create";
        }
        service.createInventories(request);
        return "redirect:/inventories";
    }

    //has bug and should be fixed
    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @GetMapping
    public String listInventories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") long roomId,
            @RequestParam(defaultValue = "") LocalDateTime date,
            Model model
    ) {
        if (date == null && roomId.toString().equals("")) {
            throw new DynamicTextException("Please provide room or date");
        }else if (date == null){
            //Show inventories by roomId
            model.addAttribute("inventoriesPage", service.findAllByRoomPaginated(roomId,
                    PageRequest.of(page, size)));
        } else if (roomId ?> 0) {
            throw new DynamicTextException("Please provide room");

        }else{
            model.addAttribute("inventoriesPage", service.findAllByDateAndRoomPaginated(date, roomId,
                    PageRequest.of(page, size)));
        }
        return "inventories/list";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var inventory = service.findInventory(id);
        UpdateInventoryRequest request = new UpdateInventoryRequest(
                inventory.id(),inventory.room().getId(), inventory.quantity());

        model.addAttribute("inventoryRequest", request);
        return "inventories/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @PostMapping("/update")
    public String updateInventory(@Valid @ModelAttribute("inventoryRequest") UpdateInventoryRequest request,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "inventories/edit";
        }
        service.updateInventory(request);
        return "redirect:/inventories";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        service.deleteInventory(id);
        return "redirect:/inventories";
    }
}
