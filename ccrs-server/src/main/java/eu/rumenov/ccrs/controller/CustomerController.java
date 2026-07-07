package eu.rumenov.ccrs.controller;

import eu.rumenov.ccrs.domain.request.CustomerCreateRequestDTO;
import eu.rumenov.ccrs.domain.response.CustomerCreateResponseDTO;
import eu.rumenov.ccrs.service.CustomerService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/auth")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerCreateResponseDTO> createCustomer(@RequestBody @NotNull CustomerCreateRequestDTO dto) {
        final String status = customerService.registerCustomer(dto.customer());
        return ResponseEntity.ok(new CustomerCreateResponseDTO(status));
    }

}
