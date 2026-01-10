package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CreateInventoryRequest(
        @NotNull(message = "Please provide room.")
        @Positive(message = "Please provide valid room id.")
        Long roomId,

        @NotNull(message = "Please provide from date.")
        LocalDateTime from,

        @NotNull(message = "Please provide from date.")
        LocalDateTime to,

        @NotNull(message = "Please provide quantity.")
        @Min(value = 0, message = "Quantity can not be negative.")
        @Max(value = 99, message = "Quantity can not be more than 99.")
        Integer quantity
) {
}
