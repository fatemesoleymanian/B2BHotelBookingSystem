package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.*;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Room;
import com.example.B2BHotelBookingSystem.models.RoomType;
import com.example.B2BHotelBookingSystem.repositories.HotelRepository;
import com.example.B2BHotelBookingSystem.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository repository;
    private final HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms(){
        List<Room> rooms = repository.findAll();
        return rooms.stream().map(RoomResponse::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> findAllPaginated(Pageable pageable){
        return repository.findAll(pageable).map(RoomResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> findAllPaginatedByHotelAndRoomType(Long hotelId, String roomReserveType,
                                                                        Pageable pageable) {
        return repository.findByHotelAndRoomType(hotelId,roomReserveType,pageable)
                .map(RoomResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> findAllPaginatedByHotelOrRoomType(Long hotelId, String roomReserveType,
                                                                 Pageable pageable) {
        return repository.findByHotelOrRoomType(hotelId,roomReserveType,pageable)
                .map(RoomResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> findAllPaginatedByHotel(Long hotelId, Pageable pageable) {
        return repository.findByHotel(hotelId,pageable)
                .map(RoomResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> findAllPaginatedByRoomType(String roomReserveType,
                                                                Pageable pageable) {
        return repository.findByRoomType(roomReserveType,pageable)
                .map(RoomResponse::fromEntity);
    }

    public RoomResponse createRoom(CreateRoomRequest request){

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.hotelId(), "Hotel "));

        Room room = Room.builder()
                .title(request.title()).mainCapacity(request.mainCapacity())
                .childCapacity(request.childCapacity())
                .roomType(RoomType.valueOf(request.roomType().toUpperCase()))
                .active(request.active())
                .hotel(hotel)
                .build();

        return mapToRoomDTo(repository.save(room));
    }

    public RoomResponse findRoom(Long id){
        Room room = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Room "));
        return RoomResponse.fromEntity(room);
    }

    public void deleteRoom(Long id){
        Room room = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Room "));
        repository.deleteById(room.getId());
    }

    @Transactional
    public RoomResponse updateRoom(UpdateRoomRequest request){
        Room room = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Room "));

        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new NotFoundException(request.hotelId(), "Hotel "));

        room.setTitle(request.title());
        room.setMainCapacity(request.mainCapacity());
        room.setChildCapacity(request.childCapacity());
        room.setRoomType(RoomType.valueOf(request.roomType().toUpperCase()));
        room.setActive(request.active());
        room.setHotel(hotel);

        return mapToRoomDTo(repository.save(room));
    }

    private RoomResponse mapToRoomDTo(Room room){
        return RoomResponse.fromEntity(room);
    }

}
