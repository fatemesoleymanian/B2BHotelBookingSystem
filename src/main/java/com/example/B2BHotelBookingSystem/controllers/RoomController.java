package com.example.B2BHotelBookingSystem.controllers;

import com.example.B2BHotelBookingSystem.dtos.CreateRoomRequest;
import com.example.B2BHotelBookingSystem.dtos.UpdateRoomRequest;
import com.example.B2BHotelBookingSystem.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController extends BaseController{

    private final RoomService service;

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("roomRequest",
                new CreateRoomRequest("", 0, 0, true, "",null));
        return "rooms/create";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @PostMapping("/create")
    public String createRoom(@Valid @ModelAttribute("roomRequest") CreateRoomRequest request,
                              BindingResult result, Model model){
        if (result.hasErrors()) {
            return "rooms/create";
        }
        service.createRoom(request);
        return "redirect:/rooms";
    }

    //has bug and should be fixed
    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL') or hasRole('AGENCY')")
    @GetMapping
    public String listRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "0") long hotelId,
            @RequestParam(defaultValue = "") String roomType,
            Model model
    ) {
        if (roomType.isEmpty() && hotelId.toString().equals("")) {
            model.addAttribute("roomsPage", service.findAllPaginated(PageRequest.of(page, size)));
        }else if (roomType.isEmpty()){
            //Show rooms by roomType
            model.addAttribute("roomsPage", service.findAllPaginatedByHotel(hotelId, PageRequest.of(page, size)));
        } else if (hotelId ?> 0) {
            model.addAttribute("roomsPage", service.findAllPaginatedByRoomType(roomType, PageRequest.of(page, size)));
        }else{
            model.addAttribute("roomsPage", service.findAllPaginatedByHotelAndRoomType(hotelId,roomType,
                    PageRequest.of(page, size)));
        }
        return "rooms/list";
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var room = service.findRoom(id);
        UpdateRoomRequest request = new UpdateRoomRequest(
                room.id(),room.title(), room.mainCapacity(), room.childCapacity(),
                room.active(),room.roomType(), room.hotel().getId());

        model.addAttribute("roomRequest", request);
        return "rooms/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @PostMapping("/update")
    public String updateRoom(@Valid @ModelAttribute("roomRequest") UpdateRoomRequest request,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "rooms/edit";
        }
        service.updateRoom(request);
        return "redirect:/rooms";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOTEL')")
    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id) {
        service.deleteRoom(id);
        return "redirect:/rooms";
    }

}
