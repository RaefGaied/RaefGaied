package org.jhipster.projetintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projetintern.domain.AuthentificationConfigurationAsserts.*;
import static org.jhipster.projetintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.projetintern.IntegrationTest;
import org.jhipster.projetintern.domain.AuthentificationConfiguration;
import org.jhipster.projetintern.repository.AuthentificationConfigurationRepository;
import org.jhipster.projetintern.service.dto.AuthentificationConfigurationDTO;
import org.jhipster.projetintern.service.mapper.AuthentificationConfigurationMapper;
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
 * Integration tests for the {@link AuthentificationConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuthentificationConfigurationResourceIT {

    private static final Boolean DEFAULT_TWO_FACTOR_ENABLED = false;
    private static final Boolean UPDATED_TWO_FACTOR_ENABLED = true;

    private static final String DEFAULT_LOGIN_PAGE_CUSTOMIZATION = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_PAGE_CUSTOMIZATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/authentification-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AuthentificationConfigurationRepository authentificationConfigurationRepository;

    @Autowired
    private AuthentificationConfigurationMapper authentificationConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthentificationConfigurationMockMvc;

    private AuthentificationConfiguration authentificationConfiguration;

    private AuthentificationConfiguration insertedAuthentificationConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthentificationConfiguration createEntity(EntityManager em) {
        AuthentificationConfiguration authentificationConfiguration = new AuthentificationConfiguration()
            .twoFactorEnabled(DEFAULT_TWO_FACTOR_ENABLED)
            .loginPageCustomization(DEFAULT_LOGIN_PAGE_CUSTOMIZATION);
        return authentificationConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuthentificationConfiguration createUpdatedEntity(EntityManager em) {
        AuthentificationConfiguration authentificationConfiguration = new AuthentificationConfiguration()
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .loginPageCustomization(UPDATED_LOGIN_PAGE_CUSTOMIZATION);
        return authentificationConfiguration;
    }

    @BeforeEach
    public void initTest() {
        authentificationConfiguration = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAuthentificationConfiguration != null) {
            authentificationConfigurationRepository.delete(insertedAuthentificationConfiguration);
            insertedAuthentificationConfiguration = null;
        }
    }

    @Test
    @Transactional
    void createAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );
        var returnedAuthentificationConfigurationDTO = om.readValue(
            restAuthentificationConfigurationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(authentificationConfigurationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AuthentificationConfigurationDTO.class
        );

        // Validate the AuthentificationConfiguration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAuthentificationConfiguration = authentificationConfigurationMapper.toEntity(returnedAuthentificationConfigurationDTO);
        assertAuthentificationConfigurationUpdatableFieldsEquals(
            returnedAuthentificationConfiguration,
            getPersistedAuthentificationConfiguration(returnedAuthentificationConfiguration)
        );

        insertedAuthentificationConfiguration = returnedAuthentificationConfiguration;
    }

    @Test
    @Transactional
    void createAuthentificationConfigurationWithExistingId() throws Exception {
        // Create the AuthentificationConfiguration with an existing ID
        authentificationConfiguration.setId(1L);
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthentificationConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthentificationConfigurations() throws Exception {
        // Initialize the database
        insertedAuthentificationConfiguration = authentificationConfigurationRepository.saveAndFlush(authentificationConfiguration);

        // Get all the authentificationConfigurationList
        restAuthentificationConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authentificationConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].twoFactorEnabled").value(hasItem(DEFAULT_TWO_FACTOR_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].loginPageCustomization").value(hasItem(DEFAULT_LOGIN_PAGE_CUSTOMIZATION)));
    }

    @Test
    @Transactional
    void getAuthentificationConfiguration() throws Exception {
        // Initialize the database
        insertedAuthentificationConfiguration = authentificationConfigurationRepository.saveAndFlush(authentificationConfiguration);

        // Get the authentificationConfiguration
        restAuthentificationConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, authentificationConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(authentificationConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.twoFactorEnabled").value(DEFAULT_TWO_FACTOR_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.loginPageCustomization").value(DEFAULT_LOGIN_PAGE_CUSTOMIZATION));
    }

    @Test
    @Transactional
    void getNonExistingAuthentificationConfiguration() throws Exception {
        // Get the authentificationConfiguration
        restAuthentificationConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAuthentificationConfiguration() throws Exception {
        // Initialize the database
        insertedAuthentificationConfiguration = authentificationConfigurationRepository.saveAndFlush(authentificationConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the authentificationConfiguration
        AuthentificationConfiguration updatedAuthentificationConfiguration = authentificationConfigurationRepository
            .findById(authentificationConfiguration.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAuthentificationConfiguration are not directly saved in db
        em.detach(updatedAuthentificationConfiguration);
        updatedAuthentificationConfiguration
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .loginPageCustomization(UPDATED_LOGIN_PAGE_CUSTOMIZATION);
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            updatedAuthentificationConfiguration
        );

        restAuthentificationConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authentificationConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAuthentificationConfigurationToMatchAllProperties(updatedAuthentificationConfiguration);
    }

    @Test
    @Transactional
    void putNonExistingAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        authentificationConfiguration.setId(longCount.incrementAndGet());

        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthentificationConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authentificationConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        authentificationConfiguration.setId(longCount.incrementAndGet());

        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        authentificationConfiguration.setId(longCount.incrementAndGet());

        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthentificationConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedAuthentificationConfiguration = authentificationConfigurationRepository.saveAndFlush(authentificationConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the authentificationConfiguration using partial update
        AuthentificationConfiguration partialUpdatedAuthentificationConfiguration = new AuthentificationConfiguration();
        partialUpdatedAuthentificationConfiguration.setId(authentificationConfiguration.getId());

        partialUpdatedAuthentificationConfiguration
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .loginPageCustomization(UPDATED_LOGIN_PAGE_CUSTOMIZATION);

        restAuthentificationConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthentificationConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAuthentificationConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the AuthentificationConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAuthentificationConfigurationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAuthentificationConfiguration, authentificationConfiguration),
            getPersistedAuthentificationConfiguration(authentificationConfiguration)
        );
    }

    @Test
    @Transactional
    void fullUpdateAuthentificationConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedAuthentificationConfiguration = authentificationConfigurationRepository.saveAndFlush(authentificationConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the authentificationConfiguration using partial update
        AuthentificationConfiguration partialUpdatedAuthentificationConfiguration = new AuthentificationConfiguration();
        partialUpdatedAuthentificationConfiguration.setId(authentificationConfiguration.getId());

        partialUpdatedAuthentificationConfiguration
            .twoFactorEnabled(UPDATED_TWO_FACTOR_ENABLED)
            .loginPageCustomization(UPDATED_LOGIN_PAGE_CUSTOMIZATION);

        restAuthentificationConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthentificationConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAuthentificationConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the AuthentificationConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAuthentificationConfigurationUpdatableFieldsEquals(
            partialUpdatedAuthentificationConfiguration,
            getPersistedAuthentificationConfiguration(partialUpdatedAuthentificationConfiguration)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        authentificationConfiguration.setId(longCount.incrementAndGet());

        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthentificationConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authentificationConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        authentificationConfiguration.setId(longCount.incrementAndGet());

        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthentificationConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        authentificationConfiguration.setId(longCount.incrementAndGet());

        // Create the AuthentificationConfiguration
        AuthentificationConfigurationDTO authentificationConfigurationDTO = authentificationConfigurationMapper.toDto(
            authentificationConfiguration
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthentificationConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(authentificationConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuthentificationConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthentificationConfiguration() throws Exception {
        // Initialize the database
        insertedAuthentificationConfiguration = authentificationConfigurationRepository.saveAndFlush(authentificationConfiguration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the authentificationConfiguration
        restAuthentificationConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, authentificationConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return authentificationConfigurationRepository.count();
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

    protected AuthentificationConfiguration getPersistedAuthentificationConfiguration(
        AuthentificationConfiguration authentificationConfiguration
    ) {
        return authentificationConfigurationRepository.findById(authentificationConfiguration.getId()).orElseThrow();
    }

    protected void assertPersistedAuthentificationConfigurationToMatchAllProperties(
        AuthentificationConfiguration expectedAuthentificationConfiguration
    ) {
        assertAuthentificationConfigurationAllPropertiesEquals(
            expectedAuthentificationConfiguration,
            getPersistedAuthentificationConfiguration(expectedAuthentificationConfiguration)
        );
    }

    protected void assertPersistedAuthentificationConfigurationToMatchUpdatableProperties(
        AuthentificationConfiguration expectedAuthentificationConfiguration
    ) {
        assertAuthentificationConfigurationAllUpdatablePropertiesEquals(
            expectedAuthentificationConfiguration,
            getPersistedAuthentificationConfiguration(expectedAuthentificationConfiguration)
        );
    }
}
