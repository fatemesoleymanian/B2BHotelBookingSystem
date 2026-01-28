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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByEmail(username)
                .orElseGet(() -> repository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail()) // id in session
                .password(user.getPassword())
                .roles(user.getRole().name()) // adds _ROLE
                .disabled(!user.isEnabled())
                .build();
    }
}
