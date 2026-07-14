package eu.rumenov.ccrs.domain.request;

import jakarta.validation.constraints.NotNull;

public record PolicyCreateRequestDTO(
        @NotNull Long customerId,
        @NotNull Long vehicleId,
        @NotNull String startDate,
        @NotNull String endDate
) {
}
