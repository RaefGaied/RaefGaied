package org.jhipster.projetintern.repository;

import org.jhipster.projetintern.domain.UIConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UIConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UIConfigurationRepository extends JpaRepository<UIConfiguration, Long> {}
