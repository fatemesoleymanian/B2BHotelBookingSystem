package com.example.B2BHotelBookingSystem.dtos.Hotel.Room;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateRoomRequest(
        @NotNull(message = "Please provide unique id.")
        @Positive(message = "id must be positive")
        Long id,

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

        @NotNull(message = "Please provide price.")
        @DecimalMin(value = "0.0", message = "Price cannot be negative.")
        BigDecimal price,
        @NotNull(message = "Active flag must be provided.")
        Boolean active,

        @NotNull(message = "Room type is required.")
        String roomType,

        @NotNull(message = "Please provide hotel.")
        Long hotelId
) {
}
