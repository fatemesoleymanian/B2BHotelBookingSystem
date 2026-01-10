package com.example.B2BHotelBookingSystem.repositories;


import com.example.B2BHotelBookingSystem.models.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    public Page<Hotel> findByCityNameContainingIgnoreCase(String cityName, Pageable pageable);

}
