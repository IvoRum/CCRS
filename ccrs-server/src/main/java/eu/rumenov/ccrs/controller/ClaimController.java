package eu.rumenov.ccrs.controller;

import eu.rumenov.ccrs.domain.request.SubmitClaimRequest;
import eu.rumenov.ccrs.domain.response.SubmitClaimResponseDTO;
import eu.rumenov.ccrs.peristence.Claim;
import eu.rumenov.ccrs.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<?> submitClaim(@RequestBody @Valid SubmitClaimRequest request) {
        try {
            Claim claim = claimService.submitClaim(request);
            return ResponseEntity.ok(new SubmitClaimResponseDTO(
                    claim.getId(), claim.getStatus(), claim.isTotalLoss()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
