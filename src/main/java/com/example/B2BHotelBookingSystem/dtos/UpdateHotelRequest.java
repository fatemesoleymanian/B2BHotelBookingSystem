package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;

public record UpdateHotelRequest(
        @NotNull(message = "Please provide unique id.")
        @Positive(message = "id must be positive")
        Long id,
        @NotBlank(message = "Hotel name is required")
        @Size(min = 2, max = 100, message = "Hotel name must be between 2 and 100 characters")
        String name,
        @Size(max = 255, message = "Address must be at most 255 characters")
        String address,

        @NotBlank(message = "City name is required")
        @Size(max = 30, message = "CityName must be at most 30 characters")
        String cityName,

        @NotNull @Min(value = 1, message = "Star can not be less than 0.")
        @Max(value = 5, message = "Star can not be greater than 5.")
        Integer star,
        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^\\+?[1-9]\\d{7,14}$",
                message = "Please provide a valid number.")
        String tel
) {
}
