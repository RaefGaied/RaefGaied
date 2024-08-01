package org.jhipster.projetintern.service;

import java.util.Optional;
import org.jhipster.projetintern.domain.EmailTemplateConfiguration;
import org.jhipster.projetintern.repository.EmailTemplateConfigurationRepository;
import org.jhipster.projetintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projetintern.service.mapper.EmailTemplateConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.jhipster.projetintern.domain.EmailTemplateConfiguration}.
 */
@Service
@Transactional
public class EmailTemplateConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(EmailTemplateConfigurationService.class);

    private final EmailTemplateConfigurationRepository emailTemplateConfigurationRepository;

    private final EmailTemplateConfigurationMapper emailTemplateConfigurationMapper;

    public EmailTemplateConfigurationService(
        EmailTemplateConfigurationRepository emailTemplateConfigurationRepository,
        EmailTemplateConfigurationMapper emailTemplateConfigurationMapper
    ) {
        this.emailTemplateConfigurationRepository = emailTemplateConfigurationRepository;
        this.emailTemplateConfigurationMapper = emailTemplateConfigurationMapper;
    }

    /**
     * Save a emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailTemplateConfigurationDTO save(EmailTemplateConfigurationDTO emailTemplateConfigurationDTO) {
        log.debug("Request to save EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);
        EmailTemplateConfiguration emailTemplateConfiguration = emailTemplateConfigurationMapper.toEntity(emailTemplateConfigurationDTO);
        emailTemplateConfiguration = emailTemplateConfigurationRepository.save(emailTemplateConfiguration);
        return emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);
    }

    /**
     * Update a emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailTemplateConfigurationDTO update(EmailTemplateConfigurationDTO emailTemplateConfigurationDTO) {
        log.debug("Request to update EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);
        EmailTemplateConfiguration emailTemplateConfiguration = emailTemplateConfigurationMapper.toEntity(emailTemplateConfigurationDTO);
        emailTemplateConfiguration = emailTemplateConfigurationRepository.save(emailTemplateConfiguration);
        return emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);
    }

    /**
     * Partially update a emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmailTemplateConfigurationDTO> partialUpdate(EmailTemplateConfigurationDTO emailTemplateConfigurationDTO) {
        log.debug("Request to partially update EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);

        return emailTemplateConfigurationRepository
            .findById(emailTemplateConfigurationDTO.getId())
            .map(existingEmailTemplateConfiguration -> {
                emailTemplateConfigurationMapper.partialUpdate(existingEmailTemplateConfiguration, emailTemplateConfigurationDTO);

                return existingEmailTemplateConfiguration;
            })
            .map(emailTemplateConfigurationRepository::save)
            .map(emailTemplateConfigurationMapper::toDto);
    }

    /**
     * Get all the emailTemplateConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailTemplateConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailTemplateConfigurations");
        return emailTemplateConfigurationRepository.findAll(pageable).map(emailTemplateConfigurationMapper::toDto);
    }

    /**
     * Get one emailTemplateConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmailTemplateConfigurationDTO> findOne(Long id) {
        log.debug("Request to get EmailTemplateConfiguration : {}", id);
        return emailTemplateConfigurationRepository.findById(id).map(emailTemplateConfigurationMapper::toDto);
    }

    /**
     * Delete the emailTemplateConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmailTemplateConfiguration : {}", id);
        emailTemplateConfigurationRepository.deleteById(id);
    }
}
