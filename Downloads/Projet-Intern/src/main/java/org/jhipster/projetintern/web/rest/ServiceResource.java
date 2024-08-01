package org.jhipster.projetintern.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.projetintern.repository.ServiceRepository;
import org.jhipster.projetintern.service.ServiceService;
import org.jhipster.projetintern.service.dto.ServiceDTO;
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
 * REST controller for managing {@link org.jhipster.projetintern.domain.Service}.
 */
@RestController
@RequestMapping("/api/services")
public class ServiceResource {

    private static final Logger log = LoggerFactory.getLogger(ServiceResource.class);

    private static final String ENTITY_NAME = "service";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceService serviceService;

    private final ServiceRepository serviceRepository;

    public ServiceResource(ServiceService serviceService, ServiceRepository serviceRepository) {
        this.serviceService = serviceService;
        this.serviceRepository = serviceRepository;
    }

    /**
     * {@code POST  /services} : Create a new service.
     *
     * @param serviceDTO the serviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceDTO, or with status {@code 400 (Bad Request)} if the service has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO) throws URISyntaxException {
        log.debug("REST request to save Service : {}", serviceDTO);
        if (serviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new service cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceDTO = serviceService.save(serviceDTO);
        return ResponseEntity.created(new URI("/api/services/" + serviceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, serviceDTO.getId().toString()))
            .body(serviceDTO);
    }

    /**
     * {@code PUT  /services/:id} : Updates an existing service.
     *
     * @param id the id of the serviceDTO to save.
     * @param serviceDTO the serviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiceDTO serviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Service : {}, {}", id, serviceDTO);
        if (serviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        serviceDTO = serviceService.update(serviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceDTO.getId().toString()))
            .body(serviceDTO);
    }

    /**
     * {@code PATCH  /services/:id} : Partial updates given fields of an existing service, field will ignore if it is null
     *
     * @param id the id of the serviceDTO to save.
     * @param serviceDTO the serviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceDTO,
     * or with status {@code 400 (Bad Request)} if the serviceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the serviceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the serviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServiceDTO> partialUpdateService(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ServiceDTO serviceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Service partially : {}, {}", id, serviceDTO);
        if (serviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, serviceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!serviceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServiceDTO> result = serviceService.partialUpdate(serviceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, serviceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /services} : get all the services.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of services in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Services");
        Page<ServiceDTO> page = serviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /services/:id} : get the "id" service.
     *
     * @param id the id of the serviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getService(@PathVariable("id") Long id) {
        log.debug("REST request to get Service : {}", id);
        Optional<ServiceDTO> serviceDTO = serviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceDTO);
    }

    /**
     * {@code DELETE  /services/:id} : delete the "id" service.
     *
     * @param id the id of the serviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") Long id) {
        log.debug("REST request to delete Service : {}", id);
        serviceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
