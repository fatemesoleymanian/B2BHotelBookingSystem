package com.example.B2BHotelBookingSystem.dtos.User.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ForgetPasswordRequest(
        @NotBlank
        @Size(max = 100, message = "do not exceed 100 characters.")
        String emailOrPhoneNumber,
        @Size(max = 6, message = "Code is not valid")
        @Size(min = 6, message = "Code is not valid")
        String resetCode
) {
}
