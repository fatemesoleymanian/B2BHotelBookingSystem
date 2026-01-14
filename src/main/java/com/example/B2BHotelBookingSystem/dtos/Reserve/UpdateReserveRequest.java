package com.example.B2BHotelBookingSystem.dtos.Reserve;


import jakarta.validation.constraints.*;

public record UpdateReserveRequest(

         @NotNull(message = "Please provide unique id.")
         @Positive(message = "id must be positive")
         Long id,

         @NotBlank(message = "Status is required.")
         String status,

         @NotNull(message = "Please provide guest firstName.")
         String guestFirstName,

         @NotNull(message = "Please provide guest lastName.")
         String guestLastName,

         @NotNull(message = "Please provide guest phone number.")
         String guestPhoneNumber,

         String description
) {
}
