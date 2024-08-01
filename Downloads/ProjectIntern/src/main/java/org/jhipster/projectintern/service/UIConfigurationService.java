package org.jhipster.projectintern.service;

import java.util.Optional;
import org.jhipster.projectintern.domain.UIConfiguration;
import org.jhipster.projectintern.repository.UIConfigurationRepository;
import org.jhipster.projectintern.service.dto.UIConfigurationDTO;
import org.jhipster.projectintern.service.mapper.UIConfigurationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.jhipster.projectintern.domain.UIConfiguration}.
 */
@Service
@Transactional
public class UIConfigurationService {

    private static final Logger log = LoggerFactory.getLogger(UIConfigurationService.class);

    private final UIConfigurationRepository uIConfigurationRepository;

    private final UIConfigurationMapper uIConfigurationMapper;

    public UIConfigurationService(UIConfigurationRepository uIConfigurationRepository, UIConfigurationMapper uIConfigurationMapper) {
        this.uIConfigurationRepository = uIConfigurationRepository;
        this.uIConfigurationMapper = uIConfigurationMapper;
    }

    /**
     * Save a uIConfiguration.
     *
     * @param uIConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public UIConfigurationDTO save(UIConfigurationDTO uIConfigurationDTO) {
        log.debug("Request to save UIConfiguration : {}", uIConfigurationDTO);
        UIConfiguration uIConfiguration = uIConfigurationMapper.toEntity(uIConfigurationDTO);
        uIConfiguration = uIConfigurationRepository.save(uIConfiguration);
        return uIConfigurationMapper.toDto(uIConfiguration);
    }

    /**
     * Update a uIConfiguration.
     *
     * @param uIConfigurationDTO the entity to save.
     * @return the persisted entity.
     */
    public UIConfigurationDTO update(UIConfigurationDTO uIConfigurationDTO) {
        log.debug("Request to update UIConfiguration : {}", uIConfigurationDTO);
        UIConfiguration uIConfiguration = uIConfigurationMapper.toEntity(uIConfigurationDTO);
        uIConfiguration = uIConfigurationRepository.save(uIConfiguration);
        return uIConfigurationMapper.toDto(uIConfiguration);
    }

    /**
     * Partially update a uIConfiguration.
     *
     * @param uIConfigurationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UIConfigurationDTO> partialUpdate(UIConfigurationDTO uIConfigurationDTO) {
        log.debug("Request to partially update UIConfiguration : {}", uIConfigurationDTO);

        return uIConfigurationRepository
            .findById(uIConfigurationDTO.getId())
            .map(existingUIConfiguration -> {
                uIConfigurationMapper.partialUpdate(existingUIConfiguration, uIConfigurationDTO);

                return existingUIConfiguration;
            })
            .map(uIConfigurationRepository::save)
            .map(uIConfigurationMapper::toDto);
    }

    /**
     * Get all the uIConfigurations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UIConfigurationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UIConfigurations");
        return uIConfigurationRepository.findAll(pageable).map(uIConfigurationMapper::toDto);
    }

    /**
     * Get one uIConfiguration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UIConfigurationDTO> findOne(Long id) {
        log.debug("Request to get UIConfiguration : {}", id);
        return uIConfigurationRepository.findById(id).map(uIConfigurationMapper::toDto);
    }

    /**
     * Delete the uIConfiguration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UIConfiguration : {}", id);
        uIConfigurationRepository.deleteById(id);
    }
}
