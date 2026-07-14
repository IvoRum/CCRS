package eu.rumenov.ccrs.repository;

import eu.rumenov.ccrs.peristence.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
