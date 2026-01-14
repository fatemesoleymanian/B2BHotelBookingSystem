package com.example.B2BHotelBookingSystem.services;


import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.Availablity.CreateInventoryRequest;
import com.example.B2BHotelBookingSystem.dtos.Availablity.InventoryResponse;
import com.example.B2BHotelBookingSystem.dtos.Availablity.UpdateInventoryRequest;
import com.example.B2BHotelBookingSystem.models.Inventory;
import com.example.B2BHotelBookingSystem.models.Room;
import com.example.B2BHotelBookingSystem.repositories.InventoryRepository;
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
public class InventoryService {

    private final InventoryRepository repository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public Page<InventoryResponse> findAllByDateAndRoomPaginated(LocalDateTime date, Long roomId, Pageable pageable){
        return repository.findAllByDateAndRoom(date, roomId, pageable).map(InventoryResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<InventoryResponse> findAllByRoomPaginated(Long roomId, Pageable pageable){
        return repository.findAllByRoom(roomId,pageable).map(InventoryResponse::fromEntity);
    }

    @Transactional
    public void createInventories(CreateInventoryRequest request){

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException(request.roomId(), "Room "));

        //it's a range
        if (request.from() != request.to()){
            Set<LocalDateTime> dates = generateDatesBetweenFromAndTo(request.from(), request.to());
            //loop through and save
            loopThroughtAndSave(dates, room,request);
        }else{ //it's a single day
            Inventory inventory = Inventory.builder()
                    .room(room).date(request.from())
                    .quantity(request.quantity())
                    .build();

            repository.save(inventory);
        }
    }

    public InventoryResponse findInventory(Long id){
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Inventory "));
        return InventoryResponse.fromEntity(inventory);
    }

    public void deleteInventory(Long id){
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Inventory "));
        repository.deleteById(inventory.getId());
    }

    @Transactional
    public InventoryResponse updateInventory(UpdateInventoryRequest request){
        Inventory inventory = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Inventory "));

        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException(request.roomId(), "Room "));

        inventory.setQuantity(request.quantity());

        return mapToInventoryDTo(repository.save(inventory));
    }

    private InventoryResponse mapToInventoryDTo(Inventory inventory){
        return InventoryResponse.fromEntity(inventory);
    }

    private Set<LocalDateTime> generateDatesBetweenFromAndTo(LocalDateTime from, LocalDateTime to){
        /**TODO **/
    }

    private void loopThroughtAndSave(Set<LocalDateTime> dates, Room room, CreateInventoryRequest request) {
        /**TODO should be more safe and optimized**/
        for (LocalDateTime date: dates) {
            Inventory inventory = Inventory.builder()
                    .room(room).date(date)
                    .quantity(request.quantity())
                    .build();

            repository.save(inventory);
        }
    }

}
