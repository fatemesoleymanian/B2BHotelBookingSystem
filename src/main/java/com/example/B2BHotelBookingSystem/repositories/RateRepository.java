package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
public interface RateRepository extends JpaRepository<Rate, Long> {
    Page<Rate> findAllByFromGreaterThan(LocalDate from, Pageable pageable);
    Page<Rate> findAllByToLessThan(LocalDate to, Pageable pageable);
    Page<Rate> findAllByHotelAndAgency(Long hotelId,Long agencyId, Pageable pageable);
    Rate findFirstByHotelAndAgency(Long hotelId,Long agencyId);

    Page<Rate> findAllByHotel(Long hotelId, Pageable pageable);
    Page<Rate> findAllByAgency(Long agencyId, Pageable pageable);
}
