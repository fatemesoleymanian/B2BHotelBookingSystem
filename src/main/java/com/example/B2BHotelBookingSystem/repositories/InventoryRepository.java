package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Availablity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Availablity, Long> {

    List<Availablity> findAllByRoom(Long roomId);
    Page<Availablity> findAllByRoom(Long roomId, Pageable pageable);
    Page<Availablity> findAllByDateAndRoom(LocalDateTime date, Long roomId, Pageable pageable);
    Availablity findByDateAndRoomOrCreate(LocalDateTime date, Long roomId);
}
