package com.example.B2BHotelBookingSystem.services;

import com.example.B2BHotelBookingSystem.config.exceptions.DynamicTextException;
import com.example.B2BHotelBookingSystem.config.exceptions.NotFoundException;
import com.example.B2BHotelBookingSystem.dtos.Agency.AgencyResponse;
import com.example.B2BHotelBookingSystem.dtos.Agency.CreateAgencyRequest;
import com.example.B2BHotelBookingSystem.dtos.Agency.UpdateAgencyRequest;
import com.example.B2BHotelBookingSystem.models.Agency;
import com.example.B2BHotelBookingSystem.repositories.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AgencyService {
    private final AgencyRepository repository;

    public void createAgency(CreateAgencyRequest request) {

        if (repository.existsByNameIgnoreCase(request.name())) {
            throw new DynamicTextException("Agency with this name already exists");
        }

        Agency agency = Agency.builder()
                .name(request.name())
                .address(request.address())
                .cityName(request.cityName())
                .tel(request.tel())
                .build();

        repository.save(agency);
    }

    public void updateAgency(UpdateAgencyRequest request) {

        Agency agency = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException("Agency"));

        agency.setName(request.name());
        agency.setAddress(request.address());
        agency.setCityName(request.cityName());
        agency.setTel(request.tel());

        // dirty checking خودش save میکنه
    }

    public void deleteAgency(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Agency");
        }
        repository.deleteById(id); // soft delete
    }

    @Transactional(readOnly = true)
    public AgencyResponse findAgency(Long id) {
        return repository.findById(id)
                .map(AgencyResponse::fromEntity)
                .orElseThrow(() -> new NotFoundException("Agency"));
    }

    @Transactional(readOnly = true)
    public Page<AgencyResponse> findAllPaginated(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AgencyResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<AgencyResponse> findAllPaginatedByCity(String city, Pageable pageable) {
        return repository.findByCityNameContainingIgnoreCase(city, pageable)
                .map(AgencyResponse::fromEntity);
    }
}
