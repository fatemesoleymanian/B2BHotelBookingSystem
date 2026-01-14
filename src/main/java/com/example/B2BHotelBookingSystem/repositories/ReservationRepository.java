package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Set;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //Find Groups For Admin
    Page<Reservation> findAllByStatus(String status, Pageable pageable);
    Page<Reservation> findAllByFromAndTo(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<Reservation> findAllByGuestPhoneNumberContains(String guestPhoneNumber, Pageable pageable);

    //Find Groups For Hotel
    Page<Reservation> findAllByHotel(Long hotelId, Pageable pageable);
    Page<Reservation> findAllByHotelAndStatus(Long hotelId,String status, Pageable pageable);
    Page<Reservation> findAllByHotelAndRooms(Long hotelId, Set<Long> rooms, Pageable pageable);
    Page<Reservation> findAllByHotelAndFromAndTo(Long hotelId,LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<Reservation> findAllByHotelAndGuestPhoneNumberContains(Long hotelId,String guestPhoneNumber, Pageable pageable);

    //Find A Reserve For Hotel
    Reservation findByHotelAndId(Long hotelId,Long id);

    //Find Groups For Agency
    Page<Reservation> findAllByAgency(Long agencyId, Pageable pageable);
    Page<Reservation> findAllByAgencyAndStatus(Long agencyId,String status, Pageable pageable);
    Page<Reservation> findAllByAgencyAndFromAndTo(Long agencyId,LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<Reservation> findAllByAgencyAndGuestPhoneNumberContains(Long agencyId,String guestPhoneNumber, Pageable pageable);

    //Find A Reserve For Agency
    Reservation findByAgencyAndId(Long agencyId,Long id);
}
