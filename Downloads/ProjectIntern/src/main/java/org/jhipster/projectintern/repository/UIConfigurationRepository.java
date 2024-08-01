package org.jhipster.projectintern.repository;

import org.jhipster.projectintern.domain.UIConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UIConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UIConfigurationRepository extends JpaRepository<UIConfiguration, Long> {}
