package eu.rumenov.ccrs.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public record CustomerDTO(
        @NotNull @Size(min = 5, max = 50) @JsonProperty("first_name") String firstName,
        @NotNull @Size(min = 5, max = 50) @JsonProperty("last_name") String lastName,
        @NotNull @Email String email,
        @NotNull @JsonProperty("date_of_birth") Date dateOfBirth,
        @NotNull @Size(min = 5, max = 30) @JsonProperty("license_number") String licenseNumber
        ) {
}
