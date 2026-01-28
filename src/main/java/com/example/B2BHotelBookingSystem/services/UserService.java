package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.User.CreateUserRequest;
import com.example.B2BHotelBookingSystem.dtos.User.UpdateUserRequest;
import com.example.B2BHotelBookingSystem.dtos.User.UserResponse;
import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.models.Hotel;
import com.example.B2BHotelBookingSystem.models.Role;
import com.example.B2BHotelBookingSystem.models.User;
import com.example.B2BHotelBookingSystem.repositories.AgencyRepository;
import com.example.B2BHotelBookingSystem.repositories.HotelRepository;
import com.example.B2BHotelBookingSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository repository;
    private final HotelRepository hotelRepository;
    private final AgencyRepository agencyRepository;
    private final PasswordEncoder encoder;


    public UserResponse createUser(CreateUserRequest request){
        if (repository.existsByUsername(request.username())){
            throw new DynamicTextException("Duplicate username.");
        }
        else if (repository.existsByEmail(request.email())){
            throw new DynamicTextException("Duplicate email.");
        }

        User user = User.builder()
                .username(request.username()).email(request.email())
                .password(encoder.encode(request.password()))
                .phone(request.phone())
                .enabled(request.enabled())//if user is registering himself (without token) it should be false(from front) to let the admin accept it, but if has token can be whatever user send
                .role(Role.valueOf(request.role().toUpperCase()))
                .agency(request.agencyId() != null ? findAgencyById(request.agencyId()) : null)
                .hotel(request.hotelId() != null ? findHotelById(request.hotelId()) : null)
                .build();

        return UserResponse.fromEntity(repository.save(user));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAllPaginated(Pageable pageable) {
        return repository.findAll(pageable).map(UserResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
         List<User> users = repository.findAll();
        return users.stream().map(UserResponse::fromEntity).collect(Collectors.toList());
    }

    public UserResponse findUser(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User "));
        return UserResponse.fromEntity(user);
    }

    @Transactional
    public UserResponse updateUser(UpdateUserRequest request){
        User user = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "User"));

        // Check for duplicate username/email only if they changed
        if (!user.getUsername().equals(request.username()) && repository.existsByUsername(request.username())) {
            throw new DynamicTextException("Username already exists.");
        }

        if (!user.getEmail().equals(request.email()) && repository.existsByEmail(request.email())) {
            throw new DynamicTextException("Email already exists.");
        }

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setEnabled(request.enabled());
        user.setRole(Role.valueOf(request.role().toUpperCase()));

        if (request.password() != null && !request.password().isBlank()) {
            user.setPassword(encoder.encode(request.password()));
        }

        // Link logic
        user.setAgency(request.agencyId() != null ? findAgencyById(request.agencyId()) : null);
        user.setHotel(request.hotelId() != null ? findHotelById(request.hotelId()) : null);

        return UserResponse.fromEntity(repository.save(user));
    }

    public void deleteUser(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "User "));

        repository.deleteById(user.getId());
    }


    private Hotel findHotelById(Long id){
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Hotel "));
        return hotel;
    }

    private Agency findAgencyById(Long id){
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Agency "));
        return agency;
    }

}
