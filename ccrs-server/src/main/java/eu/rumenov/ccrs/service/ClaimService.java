package eu.rumenov.ccrs.service;

import eu.rumenov.ccrs.domain.request.SubmitClaimRequest;
import eu.rumenov.ccrs.peristence.Claim;
import eu.rumenov.ccrs.peristence.Policy;
import eu.rumenov.ccrs.repository.ClaimRepository;
import eu.rumenov.ccrs.repository.PolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private static final BigDecimal AUTO_APPROVAL_THRESHOLD = new BigDecimal("1000");

    private final ClaimRepository claimRepository;
    private final PolicyRepository policyRepository;

    public Claim submitClaim(SubmitClaimRequest request) {
        Policy policy = policyRepository.findById(request.policyId())
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with id: " + request.policyId()));

        Date incidentDate = Date.valueOf(request.incidentDate());

        if (incidentDate.before(policy.getStartDate()) || incidentDate.after(policy.getEndDate())) {
            throw new IllegalArgumentException("Incident date is outside the policy coverage period");
        }

        boolean totalLoss = request.estimatedRepairCost().compareTo(policy.getVehicle().getValue()) >= 0;

        String status;
        if (request.estimatedRepairCost().compareTo(AUTO_APPROVAL_THRESHOLD) <= 0 && !totalLoss) {
            status = "APPROVED";
        } else {
            status = "UNDER_REVIEW";
        }

        Claim claim = Claim.builder()
                .policy(policy)
                .incidentDate(incidentDate)
                .description(request.description())
                .estimatedRepairCost(request.estimatedRepairCost())
                .status(status)
                .totalLoss(totalLoss)
                .build();

        return claimRepository.save(claim);
    }
}
