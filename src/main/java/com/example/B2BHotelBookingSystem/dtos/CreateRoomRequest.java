package com.example.B2BHotelBookingSystem.dtos;

import jakarta.validation.constraints.*;

public record CreateRoomRequest(
        @NotBlank(message = "Room title is required")
        @Size(min = 1, max = 100, message = "Room title must be between 1 and 100 characters")
        String title,

        @Max(value = 15, message = "Main capacity can not be grater than 15")
        @Min(value = 0, message = "Main capacity can not be less than 0.")
        @NotNull(message = "Please provide main capacity of the room.")
        Integer mainCapacity,

        @Max(value = 15, message = "Child capacity can not be grater than 15")
        @Min(value = 0, message = "Child capacity can not be less than 0.")
        @NotNull(message = "Please provide child capacity of the room.")
        Integer childCapacity,

        @NotNull(message = "Active flag must be provided.")
        Boolean active,

        @NotNull(message = "Room type is required.")
        String roomType,

        @NotNull(message = "Please provide hotel.")
        Long hotelId
) {
}
