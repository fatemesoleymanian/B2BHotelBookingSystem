package com.example.B2BHotelBookingSystem.dtos.Availablity;

import com.example.B2BHotelBookingSystem.models.Availablity;
import com.example.B2BHotelBookingSystem.models.Room;
import java.time.LocalDateTime;

public record InventoryResponse(
        Long id,
        Room room,
        LocalDateTime date,
        String status
) {
    public static InventoryResponse fromEntity(Availablity inventory) {
        return new InventoryResponse(
            inventory.getId(),
            inventory.getRoom(),
            inventory.getDate(),
            inventory.getStatus().name()
        );
    }
}
