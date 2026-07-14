package eu.rumenov.ccrs.controller;

import eu.rumenov.ccrs.domain.request.VehicleCreateRequestDTO;
import eu.rumenov.ccrs.domain.response.VehicleCreateResponseDTO;
import eu.rumenov.ccrs.peristence.Customer;
import eu.rumenov.ccrs.peristence.Vehicle;
import eu.rumenov.ccrs.repository.CustomerRepository;
import eu.rumenov.ccrs.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<VehicleCreateResponseDTO> createVehicle(
            @PathVariable Long customerId,
            @RequestBody @Valid VehicleCreateRequestDTO request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Vehicle vehicle = Vehicle.builder()
                .vin(request.vin())
                .make(request.make())
                .model(request.model())
                .year(request.year())
                .value(request.value())
                .customer(customer)
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);

        return ResponseEntity.ok(new VehicleCreateResponseDTO(
                saved.getId(), saved.getVin(), saved.getMake(),
                saved.getModel(), saved.getYear(), saved.getValue()
        ));
    }
}
