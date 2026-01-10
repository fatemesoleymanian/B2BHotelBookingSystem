package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
