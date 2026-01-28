package com.example.B2BHotelBookingSystem.dtos.Availablity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record AvailablityRequest(
//        @NotNull(message = "Please provide agencyId.")
        @Positive(message = "Please provide a valid agencyId.")
        Long agencyId,
        @NotNull(message = "Please provide hotelId.")
        @Positive(message = "Please provide a valid hotelId.")
        Long hotelId,

        @NotNull(message = "Please provide from date.")
        LocalDateTime from,
        @NotNull(message = "Please provide to date.")
        LocalDateTime to,

        String roomType
) {
}
