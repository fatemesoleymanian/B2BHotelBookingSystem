package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;


public record UpdateInventoryRequest(
        @NotNull(message = "Please provide unique id.")
        @Positive(message = "id must be positive")
        Long id,

        @NotNull(message = "Please provide room.")
        @Positive(message = "Please provide valid room id.")
        Long roomId,

        @NotNull(message = "Please provide quantity.")
        @Min(value = 0, message = "Quantity can not be negative.")
        @Max(value = 99, message = "Quantity can not be more than 99.")
        Integer quantity
) {
}
