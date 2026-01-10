package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    public Page<Room> findByHotelAndRoomType(Long hotelId, String roomReserveType, Pageable pageable);
    public Page<Room> findByHotelOrRoomType(Long hotelId, String roomReserveType, Pageable pageable);
    public Page<Room> findByHotel(Long hotelId, Pageable pageable);
    public Page<Room> findByRoomType(String roomReserveType, Pageable pageable);
}
