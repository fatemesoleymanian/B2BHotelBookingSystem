package com.example.B2BHotelBookingSystem.dtos.Hotel.Room;

import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Room;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RoomResponse(

        Long id,
        String title,
        Integer mainCapacity,
        Integer childCapacity,
        BigDecimal price,
        Boolean active,
        String roomType,
        Hotel hotel,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static RoomResponse fromEntity(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getMainCapacity(),
                room.getChildCapacity(),
                room.getPrice(),
                room.getActive(),
                room.getRoomType().name(),
                room.getHotel() != null ? room.getHotel() : null,
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }
}
