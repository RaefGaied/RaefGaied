package org.jhipster.projectintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projectintern.domain.HotelAdministrateurAsserts.*;
import static org.jhipster.projectintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.projectintern.IntegrationTest;
import org.jhipster.projectintern.domain.HotelAdministrateur;
import org.jhipster.projectintern.repository.HotelAdministrateurRepository;
import org.jhipster.projectintern.service.dto.HotelAdministrateurDTO;
import org.jhipster.projectintern.service.mapper.HotelAdministrateurMapper;
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
 * Integration tests for the {@link HotelAdministrateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelAdministrateurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOT_DE_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOT_DE_PASSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hotel-administrateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HotelAdministrateurRepository hotelAdministrateurRepository;

    @Autowired
    private HotelAdministrateurMapper hotelAdministrateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelAdministrateurMockMvc;

    private HotelAdministrateur hotelAdministrateur;

    private HotelAdministrateur insertedHotelAdministrateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelAdministrateur createEntity(EntityManager em) {
        HotelAdministrateur hotelAdministrateur = new HotelAdministrateur()
            .nom(DEFAULT_NOM)
            .email(DEFAULT_EMAIL)
            .motDePasse(DEFAULT_MOT_DE_PASSE);
        return hotelAdministrateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HotelAdministrateur createUpdatedEntity(EntityManager em) {
        HotelAdministrateur hotelAdministrateur = new HotelAdministrateur()
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .motDePasse(UPDATED_MOT_DE_PASSE);
        return hotelAdministrateur;
    }

    @BeforeEach
    public void initTest() {
        hotelAdministrateur = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedHotelAdministrateur != null) {
            hotelAdministrateurRepository.delete(insertedHotelAdministrateur);
            insertedHotelAdministrateur = null;
        }
    }

    @Test
    @Transactional
    void createHotelAdministrateur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);
        var returnedHotelAdministrateurDTO = om.readValue(
            restHotelAdministrateurMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelAdministrateurDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HotelAdministrateurDTO.class
        );

        // Validate the HotelAdministrateur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHotelAdministrateur = hotelAdministrateurMapper.toEntity(returnedHotelAdministrateurDTO);
        assertHotelAdministrateurUpdatableFieldsEquals(
            returnedHotelAdministrateur,
            getPersistedHotelAdministrateur(returnedHotelAdministrateur)
        );

        insertedHotelAdministrateur = returnedHotelAdministrateur;
    }

    @Test
    @Transactional
    void createHotelAdministrateurWithExistingId() throws Exception {
        // Create the HotelAdministrateur with an existing ID
        hotelAdministrateur.setId(1L);
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelAdministrateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelAdministrateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotelAdministrateurs() throws Exception {
        // Initialize the database
        insertedHotelAdministrateur = hotelAdministrateurRepository.saveAndFlush(hotelAdministrateur);

        // Get all the hotelAdministrateurList
        restHotelAdministrateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotelAdministrateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].motDePasse").value(hasItem(DEFAULT_MOT_DE_PASSE)));
    }

    @Test
    @Transactional
    void getHotelAdministrateur() throws Exception {
        // Initialize the database
        insertedHotelAdministrateur = hotelAdministrateurRepository.saveAndFlush(hotelAdministrateur);

        // Get the hotelAdministrateur
        restHotelAdministrateurMockMvc
            .perform(get(ENTITY_API_URL_ID, hotelAdministrateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotelAdministrateur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.motDePasse").value(DEFAULT_MOT_DE_PASSE));
    }

    @Test
    @Transactional
    void getNonExistingHotelAdministrateur() throws Exception {
        // Get the hotelAdministrateur
        restHotelAdministrateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotelAdministrateur() throws Exception {
        // Initialize the database
        insertedHotelAdministrateur = hotelAdministrateurRepository.saveAndFlush(hotelAdministrateur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelAdministrateur
        HotelAdministrateur updatedHotelAdministrateur = hotelAdministrateurRepository.findById(hotelAdministrateur.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHotelAdministrateur are not directly saved in db
        em.detach(updatedHotelAdministrateur);
        updatedHotelAdministrateur.nom(UPDATED_NOM).email(UPDATED_EMAIL).motDePasse(UPDATED_MOT_DE_PASSE);
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(updatedHotelAdministrateur);

        restHotelAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelAdministrateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelAdministrateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHotelAdministrateurToMatchAllProperties(updatedHotelAdministrateur);
    }

    @Test
    @Transactional
    void putNonExistingHotelAdministrateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelAdministrateur.setId(longCount.incrementAndGet());

        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelAdministrateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelAdministrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotelAdministrateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelAdministrateur.setId(longCount.incrementAndGet());

        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelAdministrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotelAdministrateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelAdministrateur.setId(longCount.incrementAndGet());

        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelAdministrateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelAdministrateurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelAdministrateurWithPatch() throws Exception {
        // Initialize the database
        insertedHotelAdministrateur = hotelAdministrateurRepository.saveAndFlush(hotelAdministrateur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelAdministrateur using partial update
        HotelAdministrateur partialUpdatedHotelAdministrateur = new HotelAdministrateur();
        partialUpdatedHotelAdministrateur.setId(hotelAdministrateur.getId());

        partialUpdatedHotelAdministrateur.nom(UPDATED_NOM).email(UPDATED_EMAIL).motDePasse(UPDATED_MOT_DE_PASSE);

        restHotelAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelAdministrateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotelAdministrateur))
            )
            .andExpect(status().isOk());

        // Validate the HotelAdministrateur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelAdministrateurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHotelAdministrateur, hotelAdministrateur),
            getPersistedHotelAdministrateur(hotelAdministrateur)
        );
    }

    @Test
    @Transactional
    void fullUpdateHotelAdministrateurWithPatch() throws Exception {
        // Initialize the database
        insertedHotelAdministrateur = hotelAdministrateurRepository.saveAndFlush(hotelAdministrateur);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotelAdministrateur using partial update
        HotelAdministrateur partialUpdatedHotelAdministrateur = new HotelAdministrateur();
        partialUpdatedHotelAdministrateur.setId(hotelAdministrateur.getId());

        partialUpdatedHotelAdministrateur.nom(UPDATED_NOM).email(UPDATED_EMAIL).motDePasse(UPDATED_MOT_DE_PASSE);

        restHotelAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotelAdministrateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotelAdministrateur))
            )
            .andExpect(status().isOk());

        // Validate the HotelAdministrateur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelAdministrateurUpdatableFieldsEquals(
            partialUpdatedHotelAdministrateur,
            getPersistedHotelAdministrateur(partialUpdatedHotelAdministrateur)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHotelAdministrateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelAdministrateur.setId(longCount.incrementAndGet());

        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelAdministrateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelAdministrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotelAdministrateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelAdministrateur.setId(longCount.incrementAndGet());

        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelAdministrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotelAdministrateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotelAdministrateur.setId(longCount.incrementAndGet());

        // Create the HotelAdministrateur
        HotelAdministrateurDTO hotelAdministrateurDTO = hotelAdministrateurMapper.toDto(hotelAdministrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hotelAdministrateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HotelAdministrateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotelAdministrateur() throws Exception {
        // Initialize the database
        insertedHotelAdministrateur = hotelAdministrateurRepository.saveAndFlush(hotelAdministrateur);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hotelAdministrateur
        restHotelAdministrateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotelAdministrateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hotelAdministrateurRepository.count();
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

    protected HotelAdministrateur getPersistedHotelAdministrateur(HotelAdministrateur hotelAdministrateur) {
        return hotelAdministrateurRepository.findById(hotelAdministrateur.getId()).orElseThrow();
    }

    protected void assertPersistedHotelAdministrateurToMatchAllProperties(HotelAdministrateur expectedHotelAdministrateur) {
        assertHotelAdministrateurAllPropertiesEquals(
            expectedHotelAdministrateur,
            getPersistedHotelAdministrateur(expectedHotelAdministrateur)
        );
    }

    protected void assertPersistedHotelAdministrateurToMatchUpdatableProperties(HotelAdministrateur expectedHotelAdministrateur) {
        assertHotelAdministrateurAllUpdatablePropertiesEquals(
            expectedHotelAdministrateur,
            getPersistedHotelAdministrateur(expectedHotelAdministrateur)
        );
    }
}
