package com.example.B2BHotelBookingSystem.config.security;

import com.example.B2BHotelBookingSystem.models.User;
import com.example.B2BHotelBookingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {

        User user = repository.findByEmail(input)
                .orElseGet(() -> repository.findByPhone(input)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone: " + input)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()) // id in session
                .password(user.getPassword())
                .roles(user.getRole().name()) // adds _ROLE
                .disabled(!user.isEnabled())
                .build();
    }
}
