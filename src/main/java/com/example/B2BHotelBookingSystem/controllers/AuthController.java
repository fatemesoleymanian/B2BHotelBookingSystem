package com.example.B2BHotelBookingSystem.controllers;

import com.example.B2BHotelBookingSystem.dtos.User.Auth.ForgetPasswordRequest;
import com.example.B2BHotelBookingSystem.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController{

    private final AuthService service;


    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }

    @GetMapping("/forget-password")
    public String showForgotPasswordForm(Model model){
        model.addAttribute("userRequest",new ForgetPasswordRequest("",""));
        return "auth/forget-pass";
    }

    @PostMapping("/forget-password")
    public String forgotPassword(@Valid @ModelAttribute("userRequest") ForgetPasswordRequest request,
                                 BindingResult result, Model model){
            service.forgetPassword(request.emailOrPhoneNumber());
        return "auth/reset-pass";
    }


    @GetMapping("/reset-password")
    public String showResetPasswordForm(Model model){
        model.addAttribute("userRequest",
                new ForgetPasswordRequest(model.getAttribute("emailOrPhoneNumber").toString(),
                        ""));
        return "auth/reset-pass";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute("userRequest") ForgetPasswordRequest request,
                                BindingResult result, Model model){
        if (request.resetCode() != null){
            service.resetPassword(request);
        }
        //tell user the current password changed to 12345678, and he should change it after he logins
        return "auth/login";
    }
}
