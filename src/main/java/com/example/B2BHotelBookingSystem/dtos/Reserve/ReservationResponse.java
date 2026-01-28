package com.example.B2BHotelBookingSystem.dtos.Reserve;

import com.example.B2BHotelBookingSystem.models.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record ReservationResponse(
         Long id,
         Agency agency,
         Hotel hotel,
         ReserveStatus status,
         Set<ReserveItem> rooms,
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
                reserve.getItems(),
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
