package com.example.B2BHotelBookingSystem.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record UpdateUserRequest(

        @NotNull(message = "Id is required.")
        @Positive(message = "Id is not valid.")
        Long id,
        @NotBlank(message = "Username is required.")
        @Size(max = 50, message = "Username must not exceed 50 characters.")
        String username,

        @NotBlank(message = "Email is required.")
        @Size(max = 100, message = "Email must not exceed 100 characters.")
        @Email(message = "Email format is invalid.")
        String email,

        @Pattern(
                regexp = "^\\+?[1-9]\\d{7,14}$",
                message = "Please provide a valid phone number.")
        String phone,

        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Size(max = 20, message = "Password must not exceed 20 characters.")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,

        @NotBlank(message = "Role is required.")
        String role,

        @NotNull(message = "Enabled flag must be provided.")
        Boolean enabled,

        Long agencyId,
        Long hotelId
) {
}
