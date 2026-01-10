package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpdateRateRequest(
        @NotNull(message = "Please provide unique id.")
        @Positive(message = "id must be positive")
        Long id,

        @NotNull(message = "Please provide room.")
        @Positive(message = "Please provide valid room id.")
        Long roomId,

        @NotNull(message = "Please provide price.")
        @DecimalMin(value = "0.0", message = "Price cannot be negative.")
        BigDecimal price,

        @Min(value = 0, message = "Discount percent can not be negative.")
        @Max(value = 99, message = "Discount percent can not be more than 99.")
        Integer discountPercent
) {
}
