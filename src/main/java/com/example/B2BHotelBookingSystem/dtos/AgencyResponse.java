package com.example.B2BHotelBookingSystem.dtos;

import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.models.Hotel;

import java.time.LocalDateTime;

public record AgencyResponse(
        Long id,
        String name,
        String address,
        String cityName,
        String tel,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static AgencyResponse fromEntity(Agency agency) {
        return new AgencyResponse(
                agency.getId(),
                agency.getName(),
                agency.getAddress(),
                agency.getCityName(),
                agency.getTel(),
                agency.getCreatedAt(),
                agency.getUpdatedAt()
        );
    }
}