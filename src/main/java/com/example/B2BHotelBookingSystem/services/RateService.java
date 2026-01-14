package com.example.B2BHotelBookingSystem.services;


import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.Hotel.Rate.CreateRateRequest;
import com.example.B2BHotelBookingSystem.dtos.Hotel.Rate.RateResponse;
import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Rate;
import com.example.B2BHotelBookingSystem.repositories.AgencyRepository;
import com.example.B2BHotelBookingSystem.repositories.HotelRepository;
import com.example.B2BHotelBookingSystem.repositories.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class RateService {

    private final RateRepository repository;
    private final HotelRepository hotelRepository;
    private final AgencyRepository agencyRepository;

    //i'm not sure what methods we need

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByHotelAndAgencyPaginated(Long hotelId, Long agencyId, Pageable pageable){
        return repository.findAllByHotelAndAgency(hotelId, agencyId, pageable).map(RateResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByHotelPaginated(Long hotelId, Pageable pageable){
        return repository.findAllByHotel(hotelId, pageable).map(RateResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByAgencyPaginated(Long agencyId, Pageable pageable){
        return repository.findAllByAgency(agencyId, pageable).map(RateResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByFromGreaterThanPaginated(LocalDate from, Pageable pageable){
        return repository.findAllByFromGreaterThan(from,pageable).map(RateResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByToLessThanPaginated(LocalDate to, Pageable pageable){
        return repository.findAllByToLessThan(to,pageable).map(RateResponse::fromEntity);
    }

    public RateResponse findRate(Long id){
        Rate rate = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Rate "));
        return RateResponse.fromEntity(rate);
    }

    @Transactional
    public void createRate(CreateRateRequest request){

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.hotelId(), "Hotel "));

        Agency agency = agencyRepository.findById(request.agencyId())
                .orElseThrow(() -> new NotFoundException(request.agencyId(), "Agency "));

        if (request.discountAmount() == null && request.discountPercent() == null){
            throw new DynamicTextException("Please provide one type of discount.(rates without discount considered as basic price.");
        }
        Rate rate = Rate.builder()
                .title(request.title())
                .hotel(hotel).agency(agency).from(request.from())
                .to(request.to())
                .discountPercent(request.discountPercent())
                .discountAmount(request.discountAmount())
                        .build();
        repository.save(rate);
    }

    private RateResponse mapToRateDTo(Rate rate){
        return RateResponse.fromEntity(rate);
    }

}
