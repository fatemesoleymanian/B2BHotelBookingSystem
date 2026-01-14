package com.example.B2BHotelBookingSystem.services;

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
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AgencyService {
    private final AgencyRepository repository;

    @Transactional(readOnly = true)
    public List<AgencyResponse> getAllAgencies(){
        List<Agency> agencies = repository.findAll();
        return agencies.stream().map(AgencyResponse::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AgencyResponse> findAllPaginated(Pageable pageable){
        return repository.findAll(pageable).map(AgencyResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<AgencyResponse> findAllPaginatedByCity(String cityName, Pageable pageable) {
        return repository.findByCityNameContainingIgnoreCase(cityName,pageable).map(AgencyResponse::fromEntity);
    }

    public AgencyResponse createAgency(CreateAgencyRequest request){

        Agency agency = Agency.builder()
                .name(request.name()).cityName(request.cityName())
                .address(request.address())
                .tel(request.tel()).build();

        return mapToAgencyDTo(repository.save(agency));
    }

    public AgencyResponse findAgency(Long id){
        Agency agency = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Agency "));
        return AgencyResponse.fromEntity(agency);
    }

    public void deleteAgency(Long id){
        Agency agency = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Agency "));
        repository.deleteById(agency.getId());
    }

    @Transactional
    public AgencyResponse updateAgency(UpdateAgencyRequest request){
        Agency agency = repository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(request.id(), "Agency "));

        agency.setName(request.name());
        agency.setAddress(request.address());
        agency.setCityName(request.cityName());
        agency.setTel(request.tel());

        return mapToAgencyDTo(repository.save(agency));
    }

    private AgencyResponse mapToAgencyDTo(Agency agency){
        return AgencyResponse.fromEntity(agency);
    }

}
