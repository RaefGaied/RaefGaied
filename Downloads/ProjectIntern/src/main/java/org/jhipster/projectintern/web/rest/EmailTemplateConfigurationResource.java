package org.jhipster.projectintern.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.projectintern.repository.EmailTemplateConfigurationRepository;
import org.jhipster.projectintern.service.EmailTemplateConfigurationService;
import org.jhipster.projectintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projectintern.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link org.jhipster.projectintern.domain.EmailTemplateConfiguration}.
 */
@RestController
@RequestMapping("/api/email-template-configurations")
public class EmailTemplateConfigurationResource {

    private static final Logger log = LoggerFactory.getLogger(EmailTemplateConfigurationResource.class);

    private static final String ENTITY_NAME = "emailTemplateConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailTemplateConfigurationService emailTemplateConfigurationService;

    private final EmailTemplateConfigurationRepository emailTemplateConfigurationRepository;

    public EmailTemplateConfigurationResource(
        EmailTemplateConfigurationService emailTemplateConfigurationService,
        EmailTemplateConfigurationRepository emailTemplateConfigurationRepository
    ) {
        this.emailTemplateConfigurationService = emailTemplateConfigurationService;
        this.emailTemplateConfigurationRepository = emailTemplateConfigurationRepository;
    }

    /**
     * {@code POST  /email-template-configurations} : Create a new emailTemplateConfiguration.
     *
     * @param emailTemplateConfigurationDTO the emailTemplateConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailTemplateConfigurationDTO, or with status {@code 400 (Bad Request)} if the emailTemplateConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EmailTemplateConfigurationDTO> createEmailTemplateConfiguration(
        @RequestBody EmailTemplateConfigurationDTO emailTemplateConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EmailTemplateConfiguration : {}", emailTemplateConfigurationDTO);
        if (emailTemplateConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailTemplateConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        emailTemplateConfigurationDTO = emailTemplateConfigurationService.save(emailTemplateConfigurationDTO);
        return ResponseEntity.created(new URI("/api/email-template-configurations/" + emailTemplateConfigurationDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, emailTemplateConfigurationDTO.getId().toString())
            )
            .body(emailTemplateConfigurationDTO);
    }

    /**
     * {@code PUT  /email-template-configurations/:id} : Updates an existing emailTemplateConfiguration.
     *
     * @param id the id of the emailTemplateConfigurationDTO to save.
     * @param emailTemplateConfigurationDTO the emailTemplateConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailTemplateConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the emailTemplateConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailTemplateConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmailTemplateConfigurationDTO> updateEmailTemplateConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailTemplateConfigurationDTO emailTemplateConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmailTemplateConfiguration : {}, {}", id, emailTemplateConfigurationDTO);
        if (emailTemplateConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailTemplateConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailTemplateConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        emailTemplateConfigurationDTO = emailTemplateConfigurationService.update(emailTemplateConfigurationDTO);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailTemplateConfigurationDTO.getId().toString())
            )
            .body(emailTemplateConfigurationDTO);
    }

    /**
     * {@code PATCH  /email-template-configurations/:id} : Partial updates given fields of an existing emailTemplateConfiguration, field will ignore if it is null
     *
     * @param id the id of the emailTemplateConfigurationDTO to save.
     * @param emailTemplateConfigurationDTO the emailTemplateConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailTemplateConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the emailTemplateConfigurationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the emailTemplateConfigurationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the emailTemplateConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmailTemplateConfigurationDTO> partialUpdateEmailTemplateConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmailTemplateConfigurationDTO emailTemplateConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmailTemplateConfiguration partially : {}, {}", id, emailTemplateConfigurationDTO);
        if (emailTemplateConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emailTemplateConfigurationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emailTemplateConfigurationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmailTemplateConfigurationDTO> result = emailTemplateConfigurationService.partialUpdate(emailTemplateConfigurationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailTemplateConfigurationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /email-template-configurations} : get all the emailTemplateConfigurations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailTemplateConfigurations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EmailTemplateConfigurationDTO>> getAllEmailTemplateConfigurations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EmailTemplateConfigurations");
        Page<EmailTemplateConfigurationDTO> page = emailTemplateConfigurationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-template-configurations/:id} : get the "id" emailTemplateConfiguration.
     *
     * @param id the id of the emailTemplateConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailTemplateConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplateConfigurationDTO> getEmailTemplateConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to get EmailTemplateConfiguration : {}", id);
        Optional<EmailTemplateConfigurationDTO> emailTemplateConfigurationDTO = emailTemplateConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailTemplateConfigurationDTO);
    }

    /**
     * {@code DELETE  /email-template-configurations/:id} : delete the "id" emailTemplateConfiguration.
     *
     * @param id the id of the emailTemplateConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailTemplateConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to delete EmailTemplateConfiguration : {}", id);
        emailTemplateConfigurationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
