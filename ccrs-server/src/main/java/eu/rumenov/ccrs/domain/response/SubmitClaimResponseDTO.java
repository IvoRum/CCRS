package eu.rumenov.ccrs.domain.response;

public record SubmitClaimResponseDTO(
        Long id,
        String status,
        boolean totalLoss
) {
}
