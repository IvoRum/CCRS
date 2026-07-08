package eu.rumenov.ccrs.peristence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    @Size(min = 5, max = 50)
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 5, max = 50)
    @NotNull
    private String lastName;

    @Column(name = "email")
    @Size(min = 5, max = 100)
    @Email
    @NotNull
    private String email;

    @Column(name = "date_of_birth")
    @NotNull
    private Date dateOfBirth;

    @Column(name = "license_number")
    @Size(min = 5, max = 30)
    @NotNull
    private String licenseNumber;
}
