package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query("""
        select r from Rate r
        where :date between r.from and r.to
        """)
    Optional<Rate> findValidRate(LocalDate date, Hotel hotel, Agency agency);

    Page<Rate> findAllByHotelAndAgency(Hotel hotel, Agency agency, Pageable pageable);
    Rate findFirstByHotelAndAgency(Hotel hotel, Agency agency);

    Page<Rate> findAllByHotel(Hotel hotel, Pageable pageable);
    Page<Rate> findAllByAgency(Agency agency, Pageable pageable);
}
