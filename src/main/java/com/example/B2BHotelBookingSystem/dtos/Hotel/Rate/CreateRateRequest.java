package com.example.B2BHotelBookingSystem.dtos.Hotel.Rate;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateRateRequest(
        @NotBlank(message = "Please provide title.")
        String title,

        @NotNull(message = "Please provide hotelId.")
        @Positive(message = "Please provide a valid hotelId.")
        Long hotelId,

        @NotNull(message = "Please provide agencyId.")
        @Positive(message = "Please provide a valid agencyId.")
        Long agencyId,

        @NotNull(message = "Please provide from date.")
        LocalDateTime from,

        @NotNull(message = "Please provide from date.")
        LocalDateTime to,

        @DecimalMin(value = "0.0", message = "Discount Amount cannot be negative.")
        BigDecimal discountAmount,

        @Min(value = 0, message = "Discount percent can not be negative.")
        @Max(value = 99, message = "Discount percent can not be more than 99.")
        Integer discountPercent
) {
}
