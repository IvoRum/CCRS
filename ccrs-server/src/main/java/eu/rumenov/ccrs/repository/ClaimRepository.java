package eu.rumenov.ccrs.repository;

import eu.rumenov.ccrs.peristence.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
}
