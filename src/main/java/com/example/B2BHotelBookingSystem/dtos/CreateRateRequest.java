package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateRateRequest(
        @NotNull(message = "Please provide room.")
        @Positive(message = "Please provide valid room id.")
        Long roomId,

        @NotNull(message = "Please provide from date.")
        LocalDateTime from,

        @NotNull(message = "Please provide from date.")
        LocalDateTime to,

        @NotNull(message = "Please provide price.")
        @DecimalMin(value = "0.0", message = "Price cannot be negative.")
        BigDecimal price,

        @Min(value = 0, message = "Discount percent can not be negative.")
        @Max(value = 99, message = "Discount percent can not be more than 99.")
        Integer discountPercent
) {
}
