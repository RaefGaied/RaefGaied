package org.jhipster.projectintern.repository;

import org.jhipster.projectintern.domain.AuthentificationConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AuthentificationConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthentificationConfigurationRepository extends JpaRepository<AuthentificationConfiguration, Long> {}
