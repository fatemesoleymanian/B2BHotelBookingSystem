package com.example.demo.services;

import com.example.B2BHotelBookingSystem.dtos.HotelCreateDTO;
import com.example.B2BHotelBookingSystem.dtos.HotelDTO;
import com.example.B2BHotelBookingSystem.dtos.HotelUpdateDTO;
import com.example.demo.models.Hotel;
import com.example.demo.repositories.HotelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository repository;

    public HotelService(HotelRepository repository){
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<HotelDTO> getAllHotels(){

        return repository.findAll().stream()
                .map(h -> mapToHotelDTo(h))
                .toList();
    }

    public HotelDTO createHotel(HotelCreateDTO hotelCreateDTO){
        Hotel hotel = new Hotel();
        hotel.setName(hotelCreateDTO.name());
        hotel.setStar(hotelCreateDTO.star());
        hotel.setAddress(hotelCreateDTO.address());
        hotel.setTel(hotelCreateDTO.tel());

        Hotel saved = repository.save(hotel);
        return mapToHotelDTo(saved);
    }

    public void deleteHotel(Long id){
        if(!repository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found");
        }
        repository.deleteById(id);
    }

    public HotelDTO updateHotel(Long id, HotelUpdateDTO hotelUpdateDTO){
        Hotel hotel = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));

        hotel.setName(hotelUpdateDTO.name());
        hotel.setStar(hotelUpdateDTO.star());
        hotel.setAddress(hotelUpdateDTO.address());
        hotel.setTel(hotelUpdateDTO.tel());

        Hotel updated = repository.save(hotel);
        return mapToHotelDTo(updated);
    }

    private HotelDTO mapToHotelDTo(Hotel hotel){
        return new HotelDTO(hotel.getId(), hotel.getName(), hotel.getAddress(), hotel.getStar(), hotel.getTel());
    }

}
