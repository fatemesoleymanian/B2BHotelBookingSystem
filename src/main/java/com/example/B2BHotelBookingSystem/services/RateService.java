package com.example.B2BHotelBookingSystem.services;


import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.*;
import com.example.B2BHotelBookingSystem.models.Rate;
import com.example.B2BHotelBookingSystem.models.Room;
import com.example.B2BHotelBookingSystem.repositories.RateRepository;
import com.example.B2BHotelBookingSystem.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RateService {

    private final RateRepository repository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByDateAndRoomPaginated(LocalDateTime date,Long roomId, Pageable pageable){
        return repository.findAllByDateAndRoom(date, roomId, pageable).map(RateResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RateResponse> findAllByRoomPaginated(Long roomId, Pageable pageable){
        return repository.findAllByRoom(roomId,pageable).map(RateResponse::fromEntity);
    }

    @Transactional
    public void createRates(CreateRateRequest request){

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException(request.roomId(), "Room "));

        //it's a range
        if (request.from() != request.to()){
            Set<LocalDateTime> dates = generateDatesBetweenFromAndTo(request.from(), request.to());
            //loop through and save
            loopThroughtAndSave(dates, room,request);
        }else{ //it's a single day
            Rate rate = Rate.builder()
                    .room(room).date(request.from())
                    .price(request.price())
                    .discountPercent(request.discountPercent())
                    .build();

            repository.save(rate);
        }
    }

    public RateResponse findRate(Long id){
        Rate rate = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Rate "));
        return RateResponse.fromEntity(rate);
    }

    public void deleteRate(Long id){
        Rate rate = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Rate "));
        repository.deleteById(rate.getId());
    }

    @Transactional
    public RateResponse updateRate(UpdateRateRequest request){
        Rate rate = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Rate "));

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException(request.roomId(), "Room "));

        rate.setPrice(request.price());
        rate.setDiscountPercent(request.discountPercent());

        return mapToRateDTo(repository.save(rate));
    }

    private RateResponse mapToRateDTo(Rate rate){
        return RateResponse.fromEntity(rate);
    }

    private Set<LocalDateTime> generateDatesBetweenFromAndTo(LocalDateTime from, LocalDateTime to){
        /**TODO **/
    }

    private void loopThroughtAndSave(Set<LocalDateTime> dates, Room room, CreateRateRequest request) {
        /**TODO should be more safe and optimized**/
        for (LocalDateTime date: dates) {
            Rate rate = Rate.builder()
                    .room(room).date(date)
                    .price(request.price())
                    .discountPercent(request.discountPercent())
                    .build();

            repository.save(rate);
        }
    }

}
