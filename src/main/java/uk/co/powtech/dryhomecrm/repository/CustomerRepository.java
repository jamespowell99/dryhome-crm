package uk.co.powtech.dryhomecrm.repository;

import uk.co.powtech.dryhomecrm.domain.Customer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
@SuppressWarnings("unused")
public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
