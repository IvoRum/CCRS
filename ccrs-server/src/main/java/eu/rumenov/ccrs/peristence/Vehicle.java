package eu.rumenov.ccrs.peristence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 11, max = 17)
    private String vin;

    @NotNull
    private String make;

    @NotNull
    private String model;

    @NotNull
    private int year;

    @NotNull
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull
    private Customer customer;
}
