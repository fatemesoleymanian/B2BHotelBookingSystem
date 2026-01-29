package com.example.B2BHotelBookingSystem.dtos.Availablity;

import com.example.B2BHotelBookingSystem.models.Availability;
import com.example.B2BHotelBookingSystem.models.Room;
import java.time.LocalDateTime;

public record InventoryResponse(
        Long id,
        Room room,
        LocalDateTime date,
        String status
) {
    public static InventoryResponse fromEntity(Availability inventory) {
        return new InventoryResponse(
            inventory.getId(),
            inventory.getRoom(),
            inventory.getCreatedAt(),
            inventory.getStatus().name()
        );
    }
}
