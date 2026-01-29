package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Room;
import com.example.B2BHotelBookingSystem.models.RoomType;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;
import java.util.Set;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Room r where r.id = :id")
    Optional<Room> findByIdForUpdate(Long id);


    @Query("""
        select r from Room r
        left join fetch r.availabilities
        where r.id = :id
        """)
    Optional<Room> findWithAvailabilities(Long id);


    public Page<Room> findByHotelAndRoomType(Hotel hotel, RoomType roomType, Pageable pageable);
    public Page<Room> findByHotel(Hotel hotel, Pageable pageable);
    public Page<Room> findByRoomType(RoomType roomType, Pageable pageable);

    Set<Room> findAllByIdIn(Set<Long> ids);
}
