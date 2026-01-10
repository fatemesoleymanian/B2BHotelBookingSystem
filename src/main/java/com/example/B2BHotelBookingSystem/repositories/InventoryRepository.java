package com.example.B2BHotelBookingSystem.repositories;

import com.example.B2BHotelBookingSystem.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
