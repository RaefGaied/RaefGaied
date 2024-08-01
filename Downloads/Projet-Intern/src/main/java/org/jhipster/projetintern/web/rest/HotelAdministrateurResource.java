package org.jhipster.projetintern.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jhipster.projetintern.repository.HotelAdministrateurRepository;
import org.jhipster.projetintern.service.HotelAdministrateurService;
import org.jhipster.projetintern.service.dto.HotelAdministrateurDTO;
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
 * REST controller for managing {@link org.jhipster.projetintern.domain.HotelAdministrateur}.
 */
@RestController
@RequestMapping("/api/hotel-administrateurs")
public class HotelAdministrateurResource {

    private static final Logger log = LoggerFactory.getLogger(HotelAdministrateurResource.class);

    private static final String ENTITY_NAME = "hotelAdministrateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HotelAdministrateurService hotelAdministrateurService;

    private final HotelAdministrateurRepository hotelAdministrateurRepository;

    public HotelAdministrateurResource(
        HotelAdministrateurService hotelAdministrateurService,
        HotelAdministrateurRepository hotelAdministrateurRepository
    ) {
        this.hotelAdministrateurService = hotelAdministrateurService;
        this.hotelAdministrateurRepository = hotelAdministrateurRepository;
    }

    /**
     * {@code POST  /hotel-administrateurs} : Create a new hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the hotelAdministrateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hotelAdministrateurDTO, or with status {@code 400 (Bad Request)} if the hotelAdministrateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HotelAdministrateurDTO> createHotelAdministrateur(@RequestBody HotelAdministrateurDTO hotelAdministrateurDTO)
        throws URISyntaxException {
        log.debug("REST request to save HotelAdministrateur : {}", hotelAdministrateurDTO);
        if (hotelAdministrateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new hotelAdministrateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hotelAdministrateurDTO = hotelAdministrateurService.save(hotelAdministrateurDTO);
        return ResponseEntity.created(new URI("/api/hotel-administrateurs/" + hotelAdministrateurDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hotelAdministrateurDTO.getId().toString()))
            .body(hotelAdministrateurDTO);
    }

    /**
     * {@code PUT  /hotel-administrateurs/:id} : Updates an existing hotelAdministrateur.
     *
     * @param id the id of the hotelAdministrateurDTO to save.
     * @param hotelAdministrateurDTO the hotelAdministrateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelAdministrateurDTO,
     * or with status {@code 400 (Bad Request)} if the hotelAdministrateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hotelAdministrateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HotelAdministrateurDTO> updateHotelAdministrateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelAdministrateurDTO hotelAdministrateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HotelAdministrateur : {}, {}", id, hotelAdministrateurDTO);
        if (hotelAdministrateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelAdministrateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelAdministrateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hotelAdministrateurDTO = hotelAdministrateurService.update(hotelAdministrateurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelAdministrateurDTO.getId().toString()))
            .body(hotelAdministrateurDTO);
    }

    /**
     * {@code PATCH  /hotel-administrateurs/:id} : Partial updates given fields of an existing hotelAdministrateur, field will ignore if it is null
     *
     * @param id the id of the hotelAdministrateurDTO to save.
     * @param hotelAdministrateurDTO the hotelAdministrateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hotelAdministrateurDTO,
     * or with status {@code 400 (Bad Request)} if the hotelAdministrateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hotelAdministrateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hotelAdministrateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HotelAdministrateurDTO> partialUpdateHotelAdministrateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HotelAdministrateurDTO hotelAdministrateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HotelAdministrateur partially : {}, {}", id, hotelAdministrateurDTO);
        if (hotelAdministrateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hotelAdministrateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hotelAdministrateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HotelAdministrateurDTO> result = hotelAdministrateurService.partialUpdate(hotelAdministrateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hotelAdministrateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hotel-administrateurs} : get all the hotelAdministrateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hotelAdministrateurs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HotelAdministrateurDTO>> getAllHotelAdministrateurs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of HotelAdministrateurs");
        Page<HotelAdministrateurDTO> page = hotelAdministrateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hotel-administrateurs/:id} : get the "id" hotelAdministrateur.
     *
     * @param id the id of the hotelAdministrateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hotelAdministrateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HotelAdministrateurDTO> getHotelAdministrateur(@PathVariable("id") Long id) {
        log.debug("REST request to get HotelAdministrateur : {}", id);
        Optional<HotelAdministrateurDTO> hotelAdministrateurDTO = hotelAdministrateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hotelAdministrateurDTO);
    }

    /**
     * {@code DELETE  /hotel-administrateurs/:id} : delete the "id" hotelAdministrateur.
     *
     * @param id the id of the hotelAdministrateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotelAdministrateur(@PathVariable("id") Long id) {
        log.debug("REST request to delete HotelAdministrateur : {}", id);
        hotelAdministrateurService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
