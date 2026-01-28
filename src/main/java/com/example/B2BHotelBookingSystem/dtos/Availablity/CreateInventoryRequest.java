package com.example.B2BHotelBookingSystem.dtos.Availablity;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record CreateInventoryRequest(
        @NotNull(message = "Please provide hotel.")
        @Positive(message = "Please provide valid hotel id.")
        Long hotelId,
        @NotNull(message = "Please provide room.")
        @Positive(message = "Please provide valid room id.")
        Long roomId,

        @NotNull(message = "Please provide from date.")
        LocalDateTime from,

        @NotNull(message = "Please provide from date.")
        LocalDateTime to,

        @NotBlank(message = "Please provide status.")
        String status
) {
}
