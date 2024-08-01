package org.jhipster.projectintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projectintern.domain.ReservationAsserts.*;
import static org.jhipster.projectintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.jhipster.projectintern.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.projectintern.IntegrationTest;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.domain.enumeration.Statut;
import org.jhipster.projectintern.repository.ReservationRepository;
import org.jhipster.projectintern.repository.UserRepository;
import org.jhipster.projectintern.service.dto.ReservationDTO;
import org.jhipster.projectintern.service.mapper.ReservationMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReservationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReservationResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_FIN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_FIN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_TOTAL_PRIX = 1F;
    private static final Float UPDATED_TOTAL_PRIX = 2F;

    private static final Statut DEFAULT_STATUT_PAIEMENT = Statut.CONFIRME;
    private static final Statut UPDATED_STATUT_PAIEMENT = Statut.EN_COURS;

    private static final String ENTITY_API_URL = "/api/reservations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReservationMockMvc;

    private Reservation reservation;

    private Reservation insertedReservation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .totalPrix(DEFAULT_TOTAL_PRIX)
            .statutPaiement(DEFAULT_STATUT_PAIEMENT);
        return reservation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reservation createUpdatedEntity(EntityManager em) {
        Reservation reservation = new Reservation()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .totalPrix(UPDATED_TOTAL_PRIX)
            .statutPaiement(UPDATED_STATUT_PAIEMENT);
        return reservation;
    }

    @BeforeEach
    public void initTest() {
        reservation = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedReservation != null) {
            reservationRepository.delete(insertedReservation);
            insertedReservation = null;
        }
    }

    @Test
    @Transactional
    void createReservation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);
        var returnedReservationDTO = om.readValue(
            restReservationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ReservationDTO.class
        );

        // Validate the Reservation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedReservation = reservationMapper.toEntity(returnedReservationDTO);
        assertReservationUpdatableFieldsEquals(returnedReservation, getPersistedReservation(returnedReservation));

        insertedReservation = returnedReservation;
    }

    @Test
    @Transactional
    void createReservationWithExistingId() throws Exception {
        // Create the Reservation with an existing ID
        reservation.setId(1L);
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReservationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllReservations() throws Exception {
        // Initialize the database
        insertedReservation = reservationRepository.saveAndFlush(reservation);

        // Get all the reservationList
        restReservationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reservation.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(sameInstant(DEFAULT_DATE_DEBUT))))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(sameInstant(DEFAULT_DATE_FIN))))
            .andExpect(jsonPath("$.[*].totalPrix").value(hasItem(DEFAULT_TOTAL_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].statutPaiement").value(hasItem(DEFAULT_STATUT_PAIEMENT.toString())));
    }

    @Test
    @Transactional
    void getReservation() throws Exception {
        // Initialize the database
        insertedReservation = reservationRepository.saveAndFlush(reservation);

        // Get the reservation
        restReservationMockMvc
            .perform(get(ENTITY_API_URL_ID, reservation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reservation.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(sameInstant(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.dateFin").value(sameInstant(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.totalPrix").value(DEFAULT_TOTAL_PRIX.doubleValue()))
            .andExpect(jsonPath("$.statutPaiement").value(DEFAULT_STATUT_PAIEMENT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingReservation() throws Exception {
        // Get the reservation
        restReservationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingReservation() throws Exception {
        // Initialize the database
        insertedReservation = reservationRepository.saveAndFlush(reservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservation
        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedReservation are not directly saved in db
        em.detach(updatedReservation);
        updatedReservation
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .totalPrix(UPDATED_TOTAL_PRIX)
            .statutPaiement(UPDATED_STATUT_PAIEMENT);
        ReservationDTO reservationDTO = reservationMapper.toDto(updatedReservation);

        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedReservationToMatchAllProperties(updatedReservation);
    }

    @Test
    @Transactional
    void putNonExistingReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservation.setId(longCount.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reservationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservation.setId(longCount.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservation.setId(longCount.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(reservationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReservationWithPatch() throws Exception {
        // Initialize the database
        insertedReservation = reservationRepository.saveAndFlush(reservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservation using partial update
        Reservation partialUpdatedReservation = new Reservation();
        partialUpdatedReservation.setId(reservation.getId());

        partialUpdatedReservation.statutPaiement(UPDATED_STATUT_PAIEMENT);

        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReservation))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReservationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedReservation, reservation),
            getPersistedReservation(reservation)
        );
    }

    @Test
    @Transactional
    void fullUpdateReservationWithPatch() throws Exception {
        // Initialize the database
        insertedReservation = reservationRepository.saveAndFlush(reservation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the reservation using partial update
        Reservation partialUpdatedReservation = new Reservation();
        partialUpdatedReservation.setId(reservation.getId());

        partialUpdatedReservation
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .totalPrix(UPDATED_TOTAL_PRIX)
            .statutPaiement(UPDATED_STATUT_PAIEMENT);

        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReservation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedReservation))
            )
            .andExpect(status().isOk());

        // Validate the Reservation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertReservationUpdatableFieldsEquals(partialUpdatedReservation, getPersistedReservation(partialUpdatedReservation));
    }

    @Test
    @Transactional
    void patchNonExistingReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservation.setId(longCount.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reservationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservation.setId(longCount.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(reservationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReservation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        reservation.setId(longCount.incrementAndGet());

        // Create the Reservation
        ReservationDTO reservationDTO = reservationMapper.toDto(reservation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReservationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(reservationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Reservation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReservation() throws Exception {
        // Initialize the database
        insertedReservation = reservationRepository.saveAndFlush(reservation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the reservation
        restReservationMockMvc
            .perform(delete(ENTITY_API_URL_ID, reservation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return reservationRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Reservation getPersistedReservation(Reservation reservation) {
        return reservationRepository.findById(reservation.getId()).orElseThrow();
    }

    protected void assertPersistedReservationToMatchAllProperties(Reservation expectedReservation) {
        assertReservationAllPropertiesEquals(expectedReservation, getPersistedReservation(expectedReservation));
    }

    protected void assertPersistedReservationToMatchUpdatableProperties(Reservation expectedReservation) {
        assertReservationAllUpdatablePropertiesEquals(expectedReservation, getPersistedReservation(expectedReservation));
    }
}
