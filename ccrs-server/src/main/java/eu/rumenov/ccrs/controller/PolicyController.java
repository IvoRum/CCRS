package eu.rumenov.ccrs.controller;

import eu.rumenov.ccrs.domain.request.PolicyCreateRequestDTO;
import eu.rumenov.ccrs.domain.response.PolicyCreateResponseDTO;
import eu.rumenov.ccrs.peristence.Customer;
import eu.rumenov.ccrs.peristence.Policy;
import eu.rumenov.ccrs.peristence.Vehicle;
import eu.rumenov.ccrs.repository.CustomerRepository;
import eu.rumenov.ccrs.repository.PolicyRepository;
import eu.rumenov.ccrs.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyRepository policyRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @PostMapping
    public ResponseEntity<PolicyCreateResponseDTO> createPolicy(@RequestBody @Valid PolicyCreateRequestDTO request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        Policy policy = Policy.builder()
                .customer(customer)
                .vehicle(vehicle)
                .startDate(Date.valueOf(request.startDate()))
                .endDate(Date.valueOf(request.endDate()))
                .build();

        Policy saved = policyRepository.save(policy);

        return ResponseEntity.ok(new PolicyCreateResponseDTO(
                saved.getId(), customer.getId(), vehicle.getId(),
                saved.getStartDate(), saved.getEndDate()
        ));
    }
}
