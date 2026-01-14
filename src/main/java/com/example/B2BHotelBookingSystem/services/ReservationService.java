package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.Reserve.CreateReserveRequest;
import com.example.B2BHotelBookingSystem.dtos.Reserve.ReservationResponse;
import com.example.B2BHotelBookingSystem.dtos.Reserve.UpdateReserveRequest;
import com.example.B2BHotelBookingSystem.models.*;
import com.example.B2BHotelBookingSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository repository;
    private final HotelRepository hotelRepository;
    private final AgencyRepository agencyRepository;
    private final RoomRepository roomRepository;
    private final RateRepository rateRepository;
    private final InventoryRepository inventoryRepository;

    public ReservationResponse findReserve(Long id){
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Reserve "));
        return ReservationResponse.fromEntity(reservation);
    }

    public ReservationResponse findReserveForHotel(Long id, Long hotelId){

        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Reserve "));
        if(!reservation.getHotel().getId().equals(hotelId)){
            throw new DynamicTextException("You have no access to this reserve");
        }
        return ReservationResponse.fromEntity(reservation);
    }

    public ReservationResponse findReserveForAgency(Long id, Long agencyId){

        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Reserve "));
        if(!reservation.getAgency().getId().equals(agencyId)){
            throw new DynamicTextException("You have no access to this reserve");
        }
        return ReservationResponse.fromEntity(reservation);
    }

    @Transactional(readOnly = true)
    public Page<ReservationResponse> findAllByStatusPaginated(String status, Pageable pageable){
        return repository.findAllByStatus(status, pageable).map(ReservationResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<ReservationResponse> findAllByHotelPaginated(Long hotelId, Pageable pageable){
        return repository.findAllByHotel(hotelId,pageable).map(ReservationResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<ReservationResponse> findAllByAgencyPaginated(Long agencyId, Pageable pageable){
        return repository.findAllByAgency(agencyId,pageable).map(ReservationResponse::fromEntity);
    }

    @Transactional
    public void createReserve(CreateReserveRequest request){

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.hotelId(), "Hotel "));

        Agency agency = agencyRepository.findById(request.agencyId())
                .orElseThrow(() -> new NotFoundException(request.agencyId(), "Agency "));

        if(!hotel.getRooms().contains(request.rooms())){
            throw new DynamicTextException("Wrong rooms for this hotel!");
        }
        boolean hasContract = request.rateId() != null;
        Rate rate = null;
        BigDecimal discountAmount = BigDecimal.ZERO;
        Integer discountPercent = 0;
        if (hasContract) {
            rate = rateRepository.findById(request.rateId())
                    .orElseThrow(() -> new NotFoundException(request.rateId(), "Rate "));


            if (!rate.getHotel().getId().equals(hotel.getId()) || !rate.getAgency().getId().equals(agency.getId())) {
                throw new DynamicTextException("Wrong rate for this reserve!");
            }
            if (rate.getFrom().isAfter(request.from()) || rate.getTo().isBefore(request.to())) {
                hasContract = false;
            }

            discountAmount = rate.getDiscountAmount();
            discountPercent = rate.getDiscountPercent();
        }
        ReserveStatus status = request.transactionId() == null ? ReserveStatus.SUBMITTED: ReserveStatus.PAID;

        Set<Room> rooms = roomRepository.findAllByIdEquals(request.rooms());

        BigDecimal totalPrice = BigDecimal.ZERO;
        if (hasContract){
            for (Room room: rooms) {
                if (hasContract){
                    if (discountPercent != 0){
                        BigDecimal price = room.getPrice();
                        BigDecimal absolutePrice = price.subtract(price.multiply((BigDecimal.valueOf(discountPercent)).divide(BigDecimal.valueOf(100))));
                        room.setPrice(absolutePrice);

                        totalPrice.add(absolutePrice);
                    }else if(discountAmount != BigDecimal.ZERO){
                        BigDecimal absolutePrice = room.getPrice().subtract(discountAmount);
                        room.setPrice(absolutePrice);

                        totalPrice.add(absolutePrice);
                    }
                }
            }
        }


        Reservation reserve = Reservation.builder()
                .hotel(hotel).agency(agency).rooms(rooms)
                .rate(rate)
                .status(status)
                .totalPrice(totalPrice)
                .from(request.from()).to(request.to())
                .guestFirstName(request.guestFirstName())
                .guestLastName(request.guestLastName())
                .guestPhoneNumber(request.guestPhoneNumber())
                .description(request.description())
                .transactionId(request.transactionId())
                 .build();

            repository.save(reserve);
            updateAvailability(rooms,request.from(),request.to(),RoomStatus.RESERVED);
    }

    @Transactional
    public ReservationResponse updateReserveStatus(UpdateReserveRequest request){
        Reservation reservation = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Reserve "));
        Set<Room> rooms = reservation.getRooms();
        ReserveStatus status = ReserveStatus.valueOf(request.status().toUpperCase());
        switch (status){
            case PAID -> {
                reservation.setStatus(status);
                //financial records could be updated
            }
            case COMPLETED -> {
                reservation.setStatus(status);
            }
            case CANCELLED -> {
                reservation.setStatus(status);
                updateAvailability(rooms,reservation.getFrom(),reservation.getTo(),RoomStatus.NOTASSIGNED);
                //financial records could be updated
            }
            case NOSHOW -> {
                reservation.setStatus(status);
                updateAvailability(rooms,reservation.getFrom(),reservation.getTo(),RoomStatus.NOTASSIGNED);
            }
            default -> {
                throw new NotFoundException("RESERVE STATUS");
            }
        }
        Reservation saved = repository.save(reservation);
        return mapToReservationDTo(saved);
    }

    @Transactional
    public ReservationResponse updateReserve(UpdateReserveRequest request){
        //description and guest info can be updated
        Reservation reservation = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Reserve "));

        reservation.setDescription(request.description());
        reservation.setGuestFirstName(request.guestFirstName());
        reservation.setGuestLastName(request.guestLastName());
        reservation.setGuestPhoneNumber(request.guestPhoneNumber());

        Reservation saved = repository.save(reservation);
        return mapToReservationDTo(saved);
    }

    private ReservationResponse mapToReservationDTo(Reservation reservation){
        return ReservationResponse.fromEntity(reservation);
    }

    private void updateAvailability(Set<Room> rooms, LocalDateTime from, LocalDateTime to, RoomStatus status) {
        //make their status cganged
        for (LocalDateTime date = from; date.isBefore(to.plusDays(1)) ; date.plusDays(1)){
            for (Room room: rooms) {
                Availablity availablity = inventoryRepository.findByDateAndRoomOrCreate(date,room.getId());
                availablity.setStatus(status);
            }
        }
    }

}
