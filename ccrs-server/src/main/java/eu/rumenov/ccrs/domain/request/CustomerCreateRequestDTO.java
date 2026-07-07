package eu.rumenov.ccrs.domain.request;

import eu.rumenov.ccrs.domain.dto.CustomerDTO;
import jakarta.validation.constraints.NotNull;

public record CustomerCreateRequestDTO(
        @NotNull CustomerDTO customer
) {
}
