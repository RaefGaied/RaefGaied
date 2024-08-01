package org.jhipster.projetintern.repository;

import org.jhipster.projetintern.domain.EmailTemplateConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmailTemplateConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplateConfigurationRepository extends JpaRepository<EmailTemplateConfiguration, Long> {}
