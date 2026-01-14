package com.example.B2BHotelBookingSystem.dtos.Reserve;

import com.example.B2BHotelBookingSystem.models.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationResponse(
         Long id,
         Agency agency,
         Hotel hotel,
         ReserveStatus status,
         List<Room> rooms,
         Long rateId,
         BigDecimal totalPrice,
         LocalDateTime from,
         LocalDateTime to,
         String guestFirstName,
         String guestLastName,
         String guestPhoneNumber,
         String description,
         String createdBy,
         String lastModifiedBy,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
) {
    public static ReservationResponse fromEntity(Reservation reserve) {
        return new ReservationResponse(
                reserve.getId(),
                reserve.getAgency(),
                reserve.getHotel(),
                reserve.getStatus(),
                reserve.getRooms(),
                reserve.getRate().getId(),
                reserve.getTotalPrice(),
                reserve.getFrom(),
                reserve.getTo(),
                reserve.getGuestFirstName(),
                reserve.getGuestLastName(),
                reserve.getGuestPhoneNumber(),
                reserve.getDescription(),
                reserve.getCreatedBy(),
                reserve.getLastModifiedBy(),
                reserve.getCreatedAt(),
                reserve.getUpdatedAt()
        );
    }

}
