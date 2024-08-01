package org.jhipster.projetintern.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.projetintern.repository.UIConfigurationRepository;
import org.jhipster.projetintern.service.UIConfigurationService;
import org.jhipster.projetintern.service.dto.UIConfigurationDTO;
import org.jhipster.projetintern.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.projetintern.domain.UIConfiguration}.
 */
@RestController
@RequestMapping("/api/ui-configurations")
public class UIConfigurationResource {

    private static final Logger log = LoggerFactory.getLogger(UIConfigurationResource.class);

    private static final String ENTITY_NAME = "uIConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UIConfigurationService uIConfigurationService;

    private final UIConfigurationRepository uIConfigurationRepository;

    public UIConfigurationResource(UIConfigurationService uIConfigurationService, UIConfigurationRepository uIConfigurationRepository) {
        this.uIConfigurationService = uIConfigurationService;
        this.uIConfigurationRepository = uIConfigurationRepository;
    }

    /**
     * {@code POST  /ui-configurations} : Create a new uIConfiguration.
     *
     * @param uIConfigurationDTO the uIConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uIConfigurationDTO, or with status {@code 400 (Bad Request)} if the uIConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UIConfigurationDTO> createUIConfiguration(@RequestBody UIConfigurationDTO uIConfigurationDTO)
        throws URISyntaxException {
        log.debug("REST request to save UIConfiguration : {}", uIConfigurationDTO);
        if (uIConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new uIConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        uIConfigurationDTO = uIConfigurationService.save(uIConfigurationDTO);
        return ResponseEntity.created(new URI("/api/ui-configurations/" + uIConfigurationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, uIConfigurationDTO.getId().toString()))
            .body(uIConfigurationDTO);
    }

    /**
     * {@code PUT  /ui-configurations/:id} : Updates an existing uIConfiguration.
     *
     * @param id the id of the uIConfigurationDTO to save.
     * @param uIConfigurationDTO the uIConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uIConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the uIConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uIConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UIConfigurationDTO> updateUIConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UIConfigurationDTO uIConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UIConfiguration : {}, {}", id, uIConfigurationDTO);
        if (uIConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uIConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uIConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        uIConfigurationDTO = uIConfigurationService.update(uIConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uIConfigurationDTO.getId().toString()))
            .body(uIConfigurationDTO);
    }

    /**
     * {@code PATCH  /ui-configurations/:id} : Partial updates given fields of an existing uIConfiguration, field will ignore if it is null
     *
     * @param id the id of the uIConfigurationDTO to save.
     * @param uIConfigurationDTO the uIConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uIConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the uIConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the uIConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the uIConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UIConfigurationDTO> partialUpdateUIConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UIConfigurationDTO uIConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UIConfiguration partially : {}, {}", id, uIConfigurationDTO);
        if (uIConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, uIConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!uIConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UIConfigurationDTO> result = uIConfigurationService.partialUpdate(uIConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uIConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ui-configurations} : get all the uIConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uIConfigurations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UIConfigurationDTO>> getAllUIConfigurations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of UIConfigurations");
        Page<UIConfigurationDTO> page = uIConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ui-configurations/:id} : get the "id" uIConfiguration.
     *
     * @param id the id of the uIConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uIConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UIConfigurationDTO> getUIConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to get UIConfiguration : {}", id);
        Optional<UIConfigurationDTO> uIConfigurationDTO = uIConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uIConfigurationDTO);
    }

    /**
     * {@code DELETE  /ui-configurations/:id} : delete the "id" uIConfiguration.
     *
     * @param id the id of the uIConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUIConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to delete UIConfiguration : {}", id);
        uIConfigurationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
