package com.example.B2BHotelBookingSystem.repositories;


import com.example.B2BHotelBookingSystem.models.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency,Long> {
    public Page<Agency> findByCityNameContainingIgnoreCase(String cityName, Pageable pageable);

}
