package com.example.B2BHotelBookingSystem.dtos;

import com.example.B2BHotelBookingSystem.models.Inventory;
import com.example.B2BHotelBookingSystem.models.Room;
import java.time.LocalDateTime;

public record InventoryResponse(
        Long id,
        Room room,
        LocalDateTime date,
        Integer quantity
) {
    public static InventoryResponse fromEntity(Inventory inventory) {
        return new InventoryResponse(
            inventory.getId(),
            inventory.getRoom(),
            inventory.getDate(),
            inventory.getQuantity()
        );
    }
}
