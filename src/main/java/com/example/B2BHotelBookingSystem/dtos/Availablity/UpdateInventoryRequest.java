package com.example.B2BHotelBookingSystem.dtos.Availablity;

import jakarta.validation.constraints.*;



public record UpdateInventoryRequest(
        @NotNull(message = "Please provide unique id.")
        @Positive(message = "id must be positive")
        Long id,

        @NotBlank(message = "Please provide status.")
        String status
) {
}
