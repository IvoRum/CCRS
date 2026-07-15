package eu.rumenov.ccrs.domain.response;

import java.math.BigDecimal;

public record VehicleCreateResponseDTO(
        Long id,
        String vin,
        String make,
        String model,
        int year,
        BigDecimal value
) {
}
