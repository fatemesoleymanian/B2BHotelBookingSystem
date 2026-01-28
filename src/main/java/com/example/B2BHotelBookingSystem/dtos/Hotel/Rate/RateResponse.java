package com.example.B2BHotelBookingSystem.dtos.Hotel.Rate;

import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Rate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RateResponse(
        Long id,
        String title,
        Hotel hotel,
        Agency agency,
        LocalDateTime from,
        LocalDateTime date,
        Integer discountPercent,
        BigDecimal discountAmount
) {
    public static RateResponse fromEntity(Rate rate) {
        return new RateResponse(
            rate.getId(),
            rate.getTitle(),
            rate.getHotel(),
            rate.getAgency(),
            rate.getFrom(),
            rate.getTo(),
            rate.getDiscountPercent(),
            rate.getDiscountAmount()
        );
    }
}
