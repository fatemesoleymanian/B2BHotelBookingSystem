package com.example.B2BHotelBookingSystem.dtos;

import com.example.B2BHotelBookingSystem.models.Rate;
import com.example.B2BHotelBookingSystem.models.Room;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RateResponse(
        Long id,
        Room room,
        LocalDateTime date,
        BigDecimal price,
        Integer discountPercent
) {
    public static RateResponse fromEntity(Rate rate) {
        return new RateResponse(
            rate.getId(),
            rate.getRoom(),
            rate.getDate(),
            rate.getPrice(),
            rate.getDiscountPercent()
        );
    }
}
