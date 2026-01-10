package com.example.B2BHotelBookingSystem.dtos;

import com.example.B2BHotelBookingSystem.models.Hotel;
import java.time.LocalDateTime;

public record HotelResponse(
        Long id,
        String name,
        String address,
        String cityName,
        Integer star,
        String tel,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static HotelResponse fromEntity(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCityName(),
                hotel.getStar(),
                hotel.getTel(),
                hotel.getCreatedAt(),
                hotel.getUpdatedAt()
        );
    }
}