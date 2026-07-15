package eu.rumenov.ccrs.domain.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record VehicleCreateRequestDTO(
        @NotNull @Size(min = 11, max = 17) String vin,
        @NotNull String make,
        @NotNull String model,
        @NotNull Integer year,
        @NotNull BigDecimal value
) {
}
