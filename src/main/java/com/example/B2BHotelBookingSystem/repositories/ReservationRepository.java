package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Reservation;
import com.example.B2BHotelBookingSystem.models.ReserveStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Reservation r where r.id = :id")
    Optional<Reservation> findByIdForUpdate(Long id);


    //Find Groups For Admin
    Page<Reservation> findAllByStatus(ReserveStatus status, Pageable pageable);
    Page<Reservation> findAllByFromAndTo(LocalDate from, LocalDate to, Pageable pageable);
    Page<Reservation> findAllByGuestPhoneNumberContains(String guestPhoneNumber, Pageable pageable);

    //Find Groups For Hotel
    Page<Reservation> findAllByHotel(Hotel hotel, Pageable pageable);
    Page<Reservation> findAllByHotelAndStatus(Hotel hotel,ReserveStatus status, Pageable pageable);
    Page<Reservation> findAllByHotelAndFromAndTo(Hotel hotel,LocalDate from, LocalDate to, Pageable pageable);
    Page<Reservation> findAllByHotelAndGuestPhoneNumberContains(Hotel hotel,String guestPhoneNumber, Pageable pageable);

    //Find A Reserve For Hotel
    Reservation findByHotelAndId(Hotel hotel, Long id);

    //Find Groups For Agency
    Page<Reservation> findAllByAgency(Agency agency, Pageable pageable);
    Page<Reservation> findAllByAgencyAndStatus(Agency agency,ReserveStatus status, Pageable pageable);
    Page<Reservation> findAllByAgencyAndFromAndTo(Agency agency,LocalDate from, LocalDate to, Pageable pageable);
    Page<Reservation> findAllByAgencyAndGuestPhoneNumberContains(Agency agency, String guestPhoneNumber, Pageable pageable);

    //Find A Reserve For Agency
    Reservation findByAgencyAndId(Agency agency,Long id);
    @Query("""
        select distinct r from Reservation r
        join r.items i
        where r.hotel = :hotel
        and i.room.id in :roomIds
        """)
    Page<Reservation> findByHotelAndRooms(Hotel hotel, Set<Long> roomIds, Pageable pageable);

}
