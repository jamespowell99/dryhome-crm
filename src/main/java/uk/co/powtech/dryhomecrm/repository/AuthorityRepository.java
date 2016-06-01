package uk.co.powtech.dryhomecrm.repository;

import uk.co.powtech.dryhomecrm.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
