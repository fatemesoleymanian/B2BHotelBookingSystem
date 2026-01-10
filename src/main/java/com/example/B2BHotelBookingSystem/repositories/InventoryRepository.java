package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findAllByRoom(Long roomId);
    Page<Inventory> findAllByRoom(Long roomId, Pageable pageable);
    Page<Inventory> findAllByDateAndRoom(LocalDateTime date, Long roomId, Pageable pageable);
}
