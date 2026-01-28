package com.example.B2BHotelBookingSystem.dtos.Availablity;

import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Room;
import java.math.BigDecimal;
import java.util.List;

public record AvailablityResponse(
        Hotel hotel,
        Long agencyId,
       List<RoomRate> rooms

) {

    public record RoomRate(
            Room room,//filled with rates based on contract(rates table)
            BigDecimal totalPrice
    ){}
}
