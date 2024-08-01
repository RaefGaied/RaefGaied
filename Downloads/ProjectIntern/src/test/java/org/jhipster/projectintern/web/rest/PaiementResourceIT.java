package org.jhipster.projectintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projectintern.domain.PaiementAsserts.*;
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
import org.jhipster.projectintern.domain.Paiement;
import org.jhipster.projectintern.repository.PaiementRepository;
import org.jhipster.projectintern.service.dto.PaiementDTO;
import org.jhipster.projectintern.service.mapper.PaiementMapper;
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
 * Integration tests for the {@link PaiementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaiementResourceIT {

    private static final Float DEFAULT_MONTANT = 1F;
    private static final Float UPDATED_MONTANT = 2F;

    private static final ZonedDateTime DEFAULT_DATE_PAIEMENT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_PAIEMENT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_METHODE_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_METHODE_PAIEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paiements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private PaiementMapper paiementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaiementMockMvc;

    private Paiement paiement;

    private Paiement insertedPaiement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiement createEntity(EntityManager em) {
        Paiement paiement = new Paiement()
            .montant(DEFAULT_MONTANT)
            .datePaiement(DEFAULT_DATE_PAIEMENT)
            .methodePaiement(DEFAULT_METHODE_PAIEMENT)
            .token(DEFAULT_TOKEN)
            .description(DEFAULT_DESCRIPTION);
        return paiement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiement createUpdatedEntity(EntityManager em) {
        Paiement paiement = new Paiement()
            .montant(UPDATED_MONTANT)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .methodePaiement(UPDATED_METHODE_PAIEMENT)
            .token(UPDATED_TOKEN)
            .description(UPDATED_DESCRIPTION);
        return paiement;
    }

    @BeforeEach
    public void initTest() {
        paiement = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPaiement != null) {
            paiementRepository.delete(insertedPaiement);
            insertedPaiement = null;
        }
    }

    @Test
    @Transactional
    void createPaiement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);
        var returnedPaiementDTO = om.readValue(
            restPaiementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paiementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PaiementDTO.class
        );

        // Validate the Paiement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPaiement = paiementMapper.toEntity(returnedPaiementDTO);
        assertPaiementUpdatableFieldsEquals(returnedPaiement, getPersistedPaiement(returnedPaiement));

        insertedPaiement = returnedPaiement;
    }

    @Test
    @Transactional
    void createPaiementWithExistingId() throws Exception {
        // Create the Paiement with an existing ID
        paiement.setId(1L);
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paiementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaiements() throws Exception {
        // Initialize the database
        insertedPaiement = paiementRepository.saveAndFlush(paiement);

        // Get all the paiementList
        restPaiementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiement.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].datePaiement").value(hasItem(sameInstant(DEFAULT_DATE_PAIEMENT))))
            .andExpect(jsonPath("$.[*].methodePaiement").value(hasItem(DEFAULT_METHODE_PAIEMENT)))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPaiement() throws Exception {
        // Initialize the database
        insertedPaiement = paiementRepository.saveAndFlush(paiement);

        // Get the paiement
        restPaiementMockMvc
            .perform(get(ENTITY_API_URL_ID, paiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paiement.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.datePaiement").value(sameInstant(DEFAULT_DATE_PAIEMENT)))
            .andExpect(jsonPath("$.methodePaiement").value(DEFAULT_METHODE_PAIEMENT))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPaiement() throws Exception {
        // Get the paiement
        restPaiementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaiement() throws Exception {
        // Initialize the database
        insertedPaiement = paiementRepository.saveAndFlush(paiement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paiement
        Paiement updatedPaiement = paiementRepository.findById(paiement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPaiement are not directly saved in db
        em.detach(updatedPaiement);
        updatedPaiement
            .montant(UPDATED_MONTANT)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .methodePaiement(UPDATED_METHODE_PAIEMENT)
            .token(UPDATED_TOKEN)
            .description(UPDATED_DESCRIPTION);
        PaiementDTO paiementDTO = paiementMapper.toDto(updatedPaiement);

        restPaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paiementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paiementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPaiementToMatchAllProperties(updatedPaiement);
    }

    @Test
    @Transactional
    void putNonExistingPaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paiement.setId(longCount.incrementAndGet());

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paiementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paiement.setId(longCount.incrementAndGet());

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(paiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paiement.setId(longCount.incrementAndGet());

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(paiementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaiementWithPatch() throws Exception {
        // Initialize the database
        insertedPaiement = paiementRepository.saveAndFlush(paiement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paiement using partial update
        Paiement partialUpdatedPaiement = new Paiement();
        partialUpdatedPaiement.setId(paiement.getId());

        partialUpdatedPaiement.methodePaiement(UPDATED_METHODE_PAIEMENT).description(UPDATED_DESCRIPTION);

        restPaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaiement))
            )
            .andExpect(status().isOk());

        // Validate the Paiement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaiementUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPaiement, paiement), getPersistedPaiement(paiement));
    }

    @Test
    @Transactional
    void fullUpdatePaiementWithPatch() throws Exception {
        // Initialize the database
        insertedPaiement = paiementRepository.saveAndFlush(paiement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the paiement using partial update
        Paiement partialUpdatedPaiement = new Paiement();
        partialUpdatedPaiement.setId(paiement.getId());

        partialUpdatedPaiement
            .montant(UPDATED_MONTANT)
            .datePaiement(UPDATED_DATE_PAIEMENT)
            .methodePaiement(UPDATED_METHODE_PAIEMENT)
            .token(UPDATED_TOKEN)
            .description(UPDATED_DESCRIPTION);

        restPaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaiement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPaiement))
            )
            .andExpect(status().isOk());

        // Validate the Paiement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPaiementUpdatableFieldsEquals(partialUpdatedPaiement, getPersistedPaiement(partialUpdatedPaiement));
    }

    @Test
    @Transactional
    void patchNonExistingPaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paiement.setId(longCount.incrementAndGet());

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paiementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paiement.setId(longCount.incrementAndGet());

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(paiementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaiement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        paiement.setId(longCount.incrementAndGet());

        // Create the Paiement
        PaiementDTO paiementDTO = paiementMapper.toDto(paiement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaiementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(paiementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Paiement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaiement() throws Exception {
        // Initialize the database
        insertedPaiement = paiementRepository.saveAndFlush(paiement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the paiement
        restPaiementMockMvc
            .perform(delete(ENTITY_API_URL_ID, paiement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return paiementRepository.count();
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

    protected Paiement getPersistedPaiement(Paiement paiement) {
        return paiementRepository.findById(paiement.getId()).orElseThrow();
    }

    protected void assertPersistedPaiementToMatchAllProperties(Paiement expectedPaiement) {
        assertPaiementAllPropertiesEquals(expectedPaiement, getPersistedPaiement(expectedPaiement));
    }

    protected void assertPersistedPaiementToMatchUpdatableProperties(Paiement expectedPaiement) {
        assertPaiementAllUpdatablePropertiesEquals(expectedPaiement, getPersistedPaiement(expectedPaiement));
    }
}
