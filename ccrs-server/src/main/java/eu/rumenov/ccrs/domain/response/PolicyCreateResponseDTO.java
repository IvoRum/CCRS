package eu.rumenov.ccrs.domain.response;

import java.sql.Date;

public record PolicyCreateResponseDTO(
        Long id,
        Long customerId,
        Long vehicleId,
        Date startDate,
        Date endDate
) {
}
