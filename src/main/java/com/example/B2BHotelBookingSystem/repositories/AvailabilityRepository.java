package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Availability;
import com.example.B2BHotelBookingSystem.models.Room;
import com.example.B2BHotelBookingSystem.models.RoomStatus;
import com.example.B2BHotelBookingSystem.models.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findAllByRoom(Room room);
    Page<Availability> findAllByRoom(Room room, Pageable pageable);
    Page<Availability> findAllByAvailableAtAndRoom(LocalDate availableAt, Room room, Pageable pageable);

    List<Availability> findAllByAvailableAtBetweenAndRoomTypeAndStatus(LocalDate from, LocalDate to, RoomType roomType, RoomStatus status);
}
