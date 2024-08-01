package org.jhipster.projetintern.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.projetintern.repository.AuthentificationConfigurationRepository;
import org.jhipster.projetintern.service.AuthentificationConfigurationService;
import org.jhipster.projetintern.service.dto.AuthentificationConfigurationDTO;
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
 * REST controller for managing {@link org.jhipster.projetintern.domain.AuthentificationConfiguration}.
 */
@RestController
@RequestMapping("/api/authentification-configurations")
public class AuthentificationConfigurationResource {

    private static final Logger log = LoggerFactory.getLogger(AuthentificationConfigurationResource.class);

    private static final String ENTITY_NAME = "authentificationConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthentificationConfigurationService authentificationConfigurationService;

    private final AuthentificationConfigurationRepository authentificationConfigurationRepository;

    public AuthentificationConfigurationResource(
        AuthentificationConfigurationService authentificationConfigurationService,
        AuthentificationConfigurationRepository authentificationConfigurationRepository
    ) {
        this.authentificationConfigurationService = authentificationConfigurationService;
        this.authentificationConfigurationRepository = authentificationConfigurationRepository;
    }

    /**
     * {@code POST  /authentification-configurations} : Create a new authentificationConfiguration.
     *
     * @param authentificationConfigurationDTO the authentificationConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authentificationConfigurationDTO, or with status {@code 400 (Bad Request)} if the authentificationConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AuthentificationConfigurationDTO> createAuthentificationConfiguration(
        @RequestBody AuthentificationConfigurationDTO authentificationConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AuthentificationConfiguration : {}", authentificationConfigurationDTO);
        if (authentificationConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new authentificationConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        authentificationConfigurationDTO = authentificationConfigurationService.save(authentificationConfigurationDTO);
        return ResponseEntity.created(new URI("/api/authentification-configurations/" + authentificationConfigurationDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    authentificationConfigurationDTO.getId().toString()
                )
            )
            .body(authentificationConfigurationDTO);
    }

    /**
     * {@code PUT  /authentification-configurations/:id} : Updates an existing authentificationConfiguration.
     *
     * @param id the id of the authentificationConfigurationDTO to save.
     * @param authentificationConfigurationDTO the authentificationConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authentificationConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the authentificationConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authentificationConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AuthentificationConfigurationDTO> updateAuthentificationConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthentificationConfigurationDTO authentificationConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AuthentificationConfiguration : {}, {}", id, authentificationConfigurationDTO);
        if (authentificationConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authentificationConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authentificationConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        authentificationConfigurationDTO = authentificationConfigurationService.update(authentificationConfigurationDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authentificationConfigurationDTO.getId().toString())
            )
            .body(authentificationConfigurationDTO);
    }

    /**
     * {@code PATCH  /authentification-configurations/:id} : Partial updates given fields of an existing authentificationConfiguration, field will ignore if it is null
     *
     * @param id the id of the authentificationConfigurationDTO to save.
     * @param authentificationConfigurationDTO the authentificationConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authentificationConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the authentificationConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the authentificationConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the authentificationConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuthentificationConfigurationDTO> partialUpdateAuthentificationConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AuthentificationConfigurationDTO authentificationConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuthentificationConfiguration partially : {}, {}", id, authentificationConfigurationDTO);
        if (authentificationConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, authentificationConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!authentificationConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuthentificationConfigurationDTO> result = authentificationConfigurationService.partialUpdate(
            authentificationConfigurationDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, authentificationConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /authentification-configurations} : get all the authentificationConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authentificationConfigurations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AuthentificationConfigurationDTO>> getAllAuthentificationConfigurations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AuthentificationConfigurations");
        Page<AuthentificationConfigurationDTO> page = authentificationConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /authentification-configurations/:id} : get the "id" authentificationConfiguration.
     *
     * @param id the id of the authentificationConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authentificationConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthentificationConfigurationDTO> getAuthentificationConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to get AuthentificationConfiguration : {}", id);
        Optional<AuthentificationConfigurationDTO> authentificationConfigurationDTO = authentificationConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(authentificationConfigurationDTO);
    }

    /**
     * {@code DELETE  /authentification-configurations/:id} : delete the "id" authentificationConfiguration.
     *
     * @param id the id of the authentificationConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthentificationConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to delete AuthentificationConfiguration : {}", id);
        authentificationConfigurationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
