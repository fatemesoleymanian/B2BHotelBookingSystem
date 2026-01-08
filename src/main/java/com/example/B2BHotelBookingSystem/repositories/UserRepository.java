package com.example.B2BHotelBookingSystem.repositories;


import com.example.B2BHotelBookingSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByPhone(String phone);
    Optional<User> findByUsernameOrEmailOrPhone(String username, String email, String phone);
}
