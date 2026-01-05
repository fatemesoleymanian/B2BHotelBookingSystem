package com.example.B2BHotelBookingSystem.controllers;

import com.example.B2BHotelBookingSystem.dtos.CreateUserRequest;
import com.example.B2BHotelBookingSystem.dtos.UpdateUserRequest;
import com.example.B2BHotelBookingSystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController{

    private final UserService service;

    //displaying create user form
    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("userRequest",
                new CreateUserRequest("", "", "", "",
                        null, true, null, null));
        return "users/create";
    }

    //save new user
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("userRequest") CreateUserRequest request,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/create";
        }
        service.createUser(request);
        return "redirect:/users";
    }

    //showing users list with pagination
    @GetMapping
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Model model
    ) {
        model.addAttribute("usersPage", service.findAllPaginated(PageRequest.of(page, size)));
        return "users/list";
    }

    //displaying edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        var user = service.findUser(id);
        UpdateUserRequest request = new UpdateUserRequest(
                user.id(), user.username(), user.email(), user.phone(),
                null,
                user.role(), user.enabled(), user.agencyId(), user.hotelId()
        );

        model.addAttribute("userRequest", request);
        return "users/edit";
    }

    //update user
    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("userRequest") UpdateUserRequest request,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "users/edit";
        }
        service.updateUser(request);
        return "redirect:/users";
    }

    //delete user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }
}

