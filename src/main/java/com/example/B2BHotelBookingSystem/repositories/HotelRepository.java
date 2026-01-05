package com.example.B2BHotelBookingSystem.repositories;


import com.example.B2BHotelBookingSystem.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
