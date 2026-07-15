package eu.rumenov.ccrs.domain.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record SubmitClaimRequest(
        @NotNull Long policyId,
        @NotNull String incidentDate,
        @NotNull String description,
        @NotNull BigDecimal estimatedRepairCost
) {
}
