package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Availablity;
import com.example.B2BHotelBookingSystem.models.RoomStatus;
import com.example.B2BHotelBookingSystem.models.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AvailablityRepository extends JpaRepository<Availablity, Long> {

    List<Availablity> findAllByRoom(Long roomId);
    Page<Availablity> findAllByRoom(Long roomId, Pageable pageable);
    Page<Availablity> findAllByDateAndRoom(LocalDateTime date, Long roomId, Pageable pageable);
    Availablity findByDateAndRoomOrCreate(LocalDateTime date, Long roomId);

    List<Availablity> findAllByDateBetweenAndRoomTypeAndStatus(LocalDateTime from, LocalDateTime to, RoomType roomType, RoomStatus status);
}
