package eu.rumenov.ccrs.peristence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    @NotNull
    private Policy policy;

    @Column(name = "incident_date")
    @NotNull
    private Date incidentDate;

    @NotNull
    private String description;

    @Column(name = "estimated_repair_cost")
    @NotNull
    private BigDecimal estimatedRepairCost;

    @NotNull
    private String status;

    @Column(name = "total_loss")
    private boolean totalLoss;
}
