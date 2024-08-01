package org.jhipster.projectintern.repository;

import org.jhipster.projectintern.domain.EmailTemplateConfiguration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmailTemplateConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTemplateConfigurationRepository extends JpaRepository<EmailTemplateConfiguration, Long> {}
