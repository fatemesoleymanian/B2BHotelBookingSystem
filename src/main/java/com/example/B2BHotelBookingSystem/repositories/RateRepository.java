package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByRoom(Long roomId);
    Page<Rate> findAllByRoom(Long roomId, Pageable pageable);
    Page<Rate> findAllByDateAndRoom(LocalDateTime date,Long roomId, Pageable pageable);
}
