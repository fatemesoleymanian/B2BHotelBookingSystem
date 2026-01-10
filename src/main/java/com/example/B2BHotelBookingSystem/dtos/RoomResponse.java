package com.example.B2BHotelBookingSystem.dtos;

import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Room;

import java.time.LocalDateTime;

public record RoomResponse(

        Long id,
        String title,
        Integer mainCapacity,
        Integer childCapacity,
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
                room.getActive(),
                room.getRoomType().name(),
                room.getHotel() != null ? room.getHotel() : null,
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }
}
