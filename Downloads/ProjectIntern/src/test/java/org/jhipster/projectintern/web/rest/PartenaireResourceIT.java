package org.jhipster.projectintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projectintern.domain.PartenaireAsserts.*;
import static org.jhipster.projectintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.projectintern.IntegrationTest;
import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.repository.PartenaireRepository;
import org.jhipster.projectintern.service.dto.PartenaireDTO;
import org.jhipster.projectintern.service.mapper.PartenaireMapper;
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
 * Integration tests for the {@link PartenaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartenaireResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_PARTENAIRE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_PARTENAIRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partenaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartenaireRepository partenaireRepository;

    @Autowired
    private PartenaireMapper partenaireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartenaireMockMvc;

    private Partenaire partenaire;

    private Partenaire insertedPartenaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaire createEntity(EntityManager em) {
        Partenaire partenaire = new Partenaire()
            .description(DEFAULT_DESCRIPTION)
            .nom(DEFAULT_NOM)
            .contact(DEFAULT_CONTACT)
            .adresse(DEFAULT_ADRESSE)
            .typePartenaire(DEFAULT_TYPE_PARTENAIRE);
        return partenaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaire createUpdatedEntity(EntityManager em) {
        Partenaire partenaire = new Partenaire()
            .description(UPDATED_DESCRIPTION)
            .nom(UPDATED_NOM)
            .contact(UPDATED_CONTACT)
            .adresse(UPDATED_ADRESSE)
            .typePartenaire(UPDATED_TYPE_PARTENAIRE);
        return partenaire;
    }

    @BeforeEach
    public void initTest() {
        partenaire = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPartenaire != null) {
            partenaireRepository.delete(insertedPartenaire);
            insertedPartenaire = null;
        }
    }

    @Test
    @Transactional
    void createPartenaire() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);
        var returnedPartenaireDTO = om.readValue(
            restPartenaireMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partenaireDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartenaireDTO.class
        );

        // Validate the Partenaire in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartenaire = partenaireMapper.toEntity(returnedPartenaireDTO);
        assertPartenaireUpdatableFieldsEquals(returnedPartenaire, getPersistedPartenaire(returnedPartenaire));

        insertedPartenaire = returnedPartenaire;
    }

    @Test
    @Transactional
    void createPartenaireWithExistingId() throws Exception {
        // Create the Partenaire with an existing ID
        partenaire.setId(1L);
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartenaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partenaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPartenaires() throws Exception {
        // Initialize the database
        insertedPartenaire = partenaireRepository.saveAndFlush(partenaire);

        // Get all the partenaireList
        restPartenaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partenaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].typePartenaire").value(hasItem(DEFAULT_TYPE_PARTENAIRE)));
    }

    @Test
    @Transactional
    void getPartenaire() throws Exception {
        // Initialize the database
        insertedPartenaire = partenaireRepository.saveAndFlush(partenaire);

        // Get the partenaire
        restPartenaireMockMvc
            .perform(get(ENTITY_API_URL_ID, partenaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partenaire.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.typePartenaire").value(DEFAULT_TYPE_PARTENAIRE));
    }

    @Test
    @Transactional
    void getNonExistingPartenaire() throws Exception {
        // Get the partenaire
        restPartenaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartenaire() throws Exception {
        // Initialize the database
        insertedPartenaire = partenaireRepository.saveAndFlush(partenaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partenaire
        Partenaire updatedPartenaire = partenaireRepository.findById(partenaire.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPartenaire are not directly saved in db
        em.detach(updatedPartenaire);
        updatedPartenaire
            .description(UPDATED_DESCRIPTION)
            .nom(UPDATED_NOM)
            .contact(UPDATED_CONTACT)
            .adresse(UPDATED_ADRESSE)
            .typePartenaire(UPDATED_TYPE_PARTENAIRE);
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(updatedPartenaire);

        restPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partenaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partenaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartenaireToMatchAllProperties(updatedPartenaire);
    }

    @Test
    @Transactional
    void putNonExistingPartenaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partenaire.setId(longCount.incrementAndGet());

        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partenaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partenaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartenaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partenaire.setId(longCount.incrementAndGet());

        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partenaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartenaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partenaire.setId(longCount.incrementAndGet());

        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partenaireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartenaireWithPatch() throws Exception {
        // Initialize the database
        insertedPartenaire = partenaireRepository.saveAndFlush(partenaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partenaire using partial update
        Partenaire partialUpdatedPartenaire = new Partenaire();
        partialUpdatedPartenaire.setId(partenaire.getId());

        partialUpdatedPartenaire.contact(UPDATED_CONTACT);

        restPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the Partenaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartenaireUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPartenaire, partenaire),
            getPersistedPartenaire(partenaire)
        );
    }

    @Test
    @Transactional
    void fullUpdatePartenaireWithPatch() throws Exception {
        // Initialize the database
        insertedPartenaire = partenaireRepository.saveAndFlush(partenaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partenaire using partial update
        Partenaire partialUpdatedPartenaire = new Partenaire();
        partialUpdatedPartenaire.setId(partenaire.getId());

        partialUpdatedPartenaire
            .description(UPDATED_DESCRIPTION)
            .nom(UPDATED_NOM)
            .contact(UPDATED_CONTACT)
            .adresse(UPDATED_ADRESSE)
            .typePartenaire(UPDATED_TYPE_PARTENAIRE);

        restPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartenaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartenaire))
            )
            .andExpect(status().isOk());

        // Validate the Partenaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartenaireUpdatableFieldsEquals(partialUpdatedPartenaire, getPersistedPartenaire(partialUpdatedPartenaire));
    }

    @Test
    @Transactional
    void patchNonExistingPartenaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partenaire.setId(longCount.incrementAndGet());

        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partenaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partenaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartenaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partenaire.setId(longCount.incrementAndGet());

        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partenaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartenaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partenaire.setId(longCount.incrementAndGet());

        // Create the Partenaire
        PartenaireDTO partenaireDTO = partenaireMapper.toDto(partenaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartenaireMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(partenaireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partenaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartenaire() throws Exception {
        // Initialize the database
        insertedPartenaire = partenaireRepository.saveAndFlush(partenaire);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partenaire
        restPartenaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, partenaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partenaireRepository.count();
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

    protected Partenaire getPersistedPartenaire(Partenaire partenaire) {
        return partenaireRepository.findById(partenaire.getId()).orElseThrow();
    }

    protected void assertPersistedPartenaireToMatchAllProperties(Partenaire expectedPartenaire) {
        assertPartenaireAllPropertiesEquals(expectedPartenaire, getPersistedPartenaire(expectedPartenaire));
    }

    protected void assertPersistedPartenaireToMatchUpdatableProperties(Partenaire expectedPartenaire) {
        assertPartenaireAllUpdatablePropertiesEquals(expectedPartenaire, getPersistedPartenaire(expectedPartenaire));
    }
}
