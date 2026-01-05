package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;

public record HotelUpdateDTO(
        @NotBlank(message = "Hotel name is required")
        @Size(min = 2, max = 100, message = "Hotel name must be between 2 and 100 characters")
        String name,
        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,
        @NotNull @Min(1) @Max(5)
        Integer star,
        @NotBlank
        @Pattern(
        regexp = "^\\+?[1-9]\\d{7,14}$",
        message = "Please provide a valid number.")
        String tel
) {
}
