package com.example.B2BHotelBookingSystem.dtos.Reserve;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record CreateReserveRequest(
        @NotNull(message = "Please provide agencyId.")
        @Positive(message = "Please provide valid agency id.")
         Long agencyId,

        @NotNull(message = "Please provide hotelId.")
        @Positive(message = "Please provide valid hotel id.")
         Long hotelId,

        @NotEmpty(message = "Please provide at least one room.")
        @Valid
         Set<Long> rooms,

        @Positive(message = "Please provide valid rate id.")
        Long rateId,

        @NotNull(message = "Please provide totalPrice.")
        @DecimalMin(value = "0.0", message = "Please provide valid totalPrice.")
        BigDecimal totalPrice,

        @NotNull(message = "Please provide from date.")
        LocalDateTime from,

        @NotNull(message = "Please provide to date.")
        LocalDateTime to,

        @NotNull(message = "Please provide guest firstName.")
         String guestFirstName,

        @NotNull(message = "Please provide guest lastName.")
        String guestLastName,

        @NotNull(message = "Please provide guest phone number.")
         String guestPhoneNumber,

         String description,
        @Positive(message = "Please provide valid transaction id.")
        Long transactionId
) {
}
