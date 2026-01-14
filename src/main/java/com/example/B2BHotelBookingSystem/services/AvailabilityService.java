package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.Availablity.AvailablityRequest;
import com.example.B2BHotelBookingSystem.dtos.Availablity.AvailablityResponse;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.repositories.HotelRepository;
import com.example.B2BHotelBookingSystem.repositories.RateRepository;
import com.example.B2BHotelBookingSystem.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AvailabilityService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RateRepository rateRepository;

    @Transactional(readOnly = true)
    public List<AvailablityResponse> searchForAvailability(AvailablityRequest request){
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.hotelId(), "Hotel "));

        //find rooms(with rates) with qty more than 0 of hotel in this date range

    }
}
