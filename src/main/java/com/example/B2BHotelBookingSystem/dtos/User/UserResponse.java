package com.example.B2BHotelBookingSystem.dtos.User;

import com.example.B2BHotelBookingSystem.models.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String email,
        String phone,
        String role,
        Boolean enabled,
        Long agencyId,
        Long hotelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name(),
                user.isEnabled(),
                user.getAgency() != null ? user.getAgency().getId() : null,
                user.getHotel() != null ? user.getHotel().getId() : null,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}