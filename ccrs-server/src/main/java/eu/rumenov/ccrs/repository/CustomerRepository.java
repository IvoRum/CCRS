package eu.rumenov.ccrs.repository;

import eu.rumenov.ccrs.peristence.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
