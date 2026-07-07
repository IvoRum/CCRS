package eu.rumenov.ccrs.service;

import eu.rumenov.ccrs.domain.dto.CustomerDTO;
import eu.rumenov.ccrs.peristence.Customer;
import eu.rumenov.ccrs.repository.CustomerRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public String registerCustomer(@NotNull CustomerDTO dto) {
        Customer customer = Customer.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .dateOfBirth(dto.dateOfBirth())
                .licenseNumber(dto.licenseNumber())
                .build();
        try {
            customerRepository.save(customer);
        }catch (Exception e) {
            return "Customer already exists";
        }
        return "Customer created";
    }
}
