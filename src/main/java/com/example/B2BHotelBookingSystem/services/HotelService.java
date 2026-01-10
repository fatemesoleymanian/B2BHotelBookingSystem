package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.CreateHotelRequest;
import com.example.B2BHotelBookingSystem.dtos.HotelResponse;
import com.example.B2BHotelBookingSystem.dtos.UpdateHotelRequest;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {
    private final HotelRepository repository;

    @Transactional(readOnly = true)
    public List<HotelResponse> getAllHotels(){
        List<Hotel> hotels = repository.findAll();
        return hotels.stream().map(HotelResponse::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<HotelResponse> findAllPaginated(Pageable pageable){
        return repository.findAll(pageable).map(HotelResponse::fromEntity);
    }

    @Transactional(readOnly = true)

    public Page<HotelResponse> findAllPaginatedByCity(String cityName, Pageable pageable) {
        return repository.findByCityNameContainingIgnoreCase(cityName,pageable).map(HotelResponse::fromEntity);
    }

    public HotelResponse createHotel(CreateHotelRequest request){

        Hotel hotel = Hotel.builder()
                .name(request.name()).cityName(request.cityName())
                .address(request.address()).star(request.star())
                .tel(request.tel()).build();

        return mapToHotelDTo(repository.save(hotel));
    }

    public HotelResponse findHotel(Long id){
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Hotel "));
        return HotelResponse.fromEntity(hotel);
    }

    public void deleteHotel(Long id){
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Hotel "));
        repository.deleteById(hotel.getId());
    }

    @Transactional
    public HotelResponse updateHotel(UpdateHotelRequest request){
        Hotel hotel = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Hotel "));

        hotel.setName(request.name());
        hotel.setStar(request.star());
        hotel.setAddress(request.address());
        hotel.setCityName(request.cityName());
        hotel.setTel(request.tel());

        return mapToHotelDTo(repository.save(hotel));
    }

    private HotelResponse mapToHotelDTo(Hotel hotel){
        return HotelResponse.fromEntity(hotel);
    }

}
