package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.Availablity.AvailablityRequest;
import com.example.B2BHotelBookingSystem.dtos.Availablity.AvailablityResponse;
import com.example.B2BHotelBookingSystem.models.*;
import com.example.B2BHotelBookingSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AvailabilityService {

    private final HotelRepository hotelRepository;
    private final AgencyRepository agencyRepository;
    private final RateRepository rateRepository;
    private final AvailablityRepository inventoryRepository;

    @Transactional(readOnly = true)
    public AvailablityResponse searchForAvailability(AvailablityRequest request){
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.hotelId(), "Hotel "));

        Agency agency = agencyRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.agencyId(), "Agency "));//it's from agency

//        Integer nights = request.from() - request.to();
        Integer nights = 2;
        //find rooms(with rates) with qty more than 0 of hotel in this date range
        List<Availablity> availablities = inventoryRepository.findAllByDateBetweenAndRoomTypeAndStatus(
                request.from(),request.to(), RoomType.valueOf(request.roomType().toUpperCase()),
                RoomStatus.NOTASSIGNED);
        Set<Room> rooms = new HashSet<>();
        for (Availablity avail: availablities) {
            rooms.add(avail.getRoom());
        }


        Rate rate = rateRepository.findFirstByHotelAndAgency(hotel.getId(),agency.getId());
        boolean hasContract = rate.getFrom().isBefore(request.from().plusDays(1)) && rate.getTo().isAfter(request.to().minusDays(1));
        BigDecimal discountAmount = BigDecimal.ZERO;
        Integer discountPercent = 0;
        if (hasContract) {
            discountAmount = rate.getDiscountAmount();
            discountPercent = rate.getDiscountPercent();
        }

        List<AvailablityResponse.RoomRate> roomRates = new ArrayList<>();
        for (Room room: rooms) {
            if (hasContract){

            }
            roomRates.add(new AvailablityResponse.RoomRate(room,room.getPrice().multiply(BigDecimal.valueOf(nights))));
        }

        return new AvailablityResponse(hotel,agency.getId(),roomRates);
    }
}
