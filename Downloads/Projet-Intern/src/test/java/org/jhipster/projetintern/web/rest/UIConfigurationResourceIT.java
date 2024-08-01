package org.jhipster.projetintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projetintern.domain.UIConfigurationAsserts.*;
import static org.jhipster.projetintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.jhipster.projetintern.web.rest.TestUtil.sameInstant;
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
import org.jhipster.projetintern.IntegrationTest;
import org.jhipster.projetintern.domain.UIConfiguration;
import org.jhipster.projetintern.repository.UIConfigurationRepository;
import org.jhipster.projetintern.service.dto.UIConfigurationDTO;
import org.jhipster.projetintern.service.mapper.UIConfigurationMapper;
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
 * Integration tests for the {@link UIConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UIConfigurationResourceIT {

    private static final String DEFAULT_COLOR_SCHEMA = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_SCHEMA = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER = "AAAAAAAAAA";
    private static final String UPDATED_BANNER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIFY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/ui-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UIConfigurationRepository uIConfigurationRepository;

    @Autowired
    private UIConfigurationMapper uIConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUIConfigurationMockMvc;

    private UIConfiguration uIConfiguration;

    private UIConfiguration insertedUIConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UIConfiguration createEntity(EntityManager em) {
        UIConfiguration uIConfiguration = new UIConfiguration()
            .colorSchema(DEFAULT_COLOR_SCHEMA)
            .logo(DEFAULT_LOGO)
            .banner(DEFAULT_BANNER)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModify(DEFAULT_DATE_MODIFY);
        return uIConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UIConfiguration createUpdatedEntity(EntityManager em) {
        UIConfiguration uIConfiguration = new UIConfiguration()
            .colorSchema(UPDATED_COLOR_SCHEMA)
            .logo(UPDATED_LOGO)
            .banner(UPDATED_BANNER)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY);
        return uIConfiguration;
    }

    @BeforeEach
    public void initTest() {
        uIConfiguration = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedUIConfiguration != null) {
            uIConfigurationRepository.delete(insertedUIConfiguration);
            insertedUIConfiguration = null;
        }
    }

    @Test
    @Transactional
    void createUIConfiguration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);
        var returnedUIConfigurationDTO = om.readValue(
            restUIConfigurationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uIConfigurationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UIConfigurationDTO.class
        );

        // Validate the UIConfiguration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedUIConfiguration = uIConfigurationMapper.toEntity(returnedUIConfigurationDTO);
        assertUIConfigurationUpdatableFieldsEquals(returnedUIConfiguration, getPersistedUIConfiguration(returnedUIConfiguration));

        insertedUIConfiguration = returnedUIConfiguration;
    }

    @Test
    @Transactional
    void createUIConfigurationWithExistingId() throws Exception {
        // Create the UIConfiguration with an existing ID
        uIConfiguration.setId(1L);
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUIConfigurationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uIConfigurationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUIConfigurations() throws Exception {
        // Initialize the database
        insertedUIConfiguration = uIConfigurationRepository.saveAndFlush(uIConfiguration);

        // Get all the uIConfigurationList
        restUIConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uIConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].colorSchema").value(hasItem(DEFAULT_COLOR_SCHEMA)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(DEFAULT_BANNER)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(sameInstant(DEFAULT_DATE_CREATION))))
            .andExpect(jsonPath("$.[*].dateModify").value(hasItem(sameInstant(DEFAULT_DATE_MODIFY))));
    }

    @Test
    @Transactional
    void getUIConfiguration() throws Exception {
        // Initialize the database
        insertedUIConfiguration = uIConfigurationRepository.saveAndFlush(uIConfiguration);

        // Get the uIConfiguration
        restUIConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, uIConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uIConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.colorSchema").value(DEFAULT_COLOR_SCHEMA))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.banner").value(DEFAULT_BANNER))
            .andExpect(jsonPath("$.dateCreation").value(sameInstant(DEFAULT_DATE_CREATION)))
            .andExpect(jsonPath("$.dateModify").value(sameInstant(DEFAULT_DATE_MODIFY)));
    }

    @Test
    @Transactional
    void getNonExistingUIConfiguration() throws Exception {
        // Get the uIConfiguration
        restUIConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUIConfiguration() throws Exception {
        // Initialize the database
        insertedUIConfiguration = uIConfigurationRepository.saveAndFlush(uIConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uIConfiguration
        UIConfiguration updatedUIConfiguration = uIConfigurationRepository.findById(uIConfiguration.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedUIConfiguration are not directly saved in db
        em.detach(updatedUIConfiguration);
        updatedUIConfiguration
            .colorSchema(UPDATED_COLOR_SCHEMA)
            .logo(UPDATED_LOGO)
            .banner(UPDATED_BANNER)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY);
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(updatedUIConfiguration);

        restUIConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uIConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uIConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUIConfigurationToMatchAllProperties(updatedUIConfiguration);
    }

    @Test
    @Transactional
    void putNonExistingUIConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uIConfiguration.setId(longCount.incrementAndGet());

        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUIConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, uIConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uIConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUIConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uIConfiguration.setId(longCount.incrementAndGet());

        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUIConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(uIConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUIConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uIConfiguration.setId(longCount.incrementAndGet());

        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUIConfigurationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(uIConfigurationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUIConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedUIConfiguration = uIConfigurationRepository.saveAndFlush(uIConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uIConfiguration using partial update
        UIConfiguration partialUpdatedUIConfiguration = new UIConfiguration();
        partialUpdatedUIConfiguration.setId(uIConfiguration.getId());

        partialUpdatedUIConfiguration.dateCreation(UPDATED_DATE_CREATION).dateModify(UPDATED_DATE_MODIFY);

        restUIConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUIConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUIConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the UIConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUIConfigurationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUIConfiguration, uIConfiguration),
            getPersistedUIConfiguration(uIConfiguration)
        );
    }

    @Test
    @Transactional
    void fullUpdateUIConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedUIConfiguration = uIConfigurationRepository.saveAndFlush(uIConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the uIConfiguration using partial update
        UIConfiguration partialUpdatedUIConfiguration = new UIConfiguration();
        partialUpdatedUIConfiguration.setId(uIConfiguration.getId());

        partialUpdatedUIConfiguration
            .colorSchema(UPDATED_COLOR_SCHEMA)
            .logo(UPDATED_LOGO)
            .banner(UPDATED_BANNER)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY);

        restUIConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUIConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUIConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the UIConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUIConfigurationUpdatableFieldsEquals(
            partialUpdatedUIConfiguration,
            getPersistedUIConfiguration(partialUpdatedUIConfiguration)
        );
    }

    @Test
    @Transactional
    void patchNonExistingUIConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uIConfiguration.setId(longCount.incrementAndGet());

        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUIConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, uIConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uIConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUIConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uIConfiguration.setId(longCount.incrementAndGet());

        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUIConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(uIConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUIConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        uIConfiguration.setId(longCount.incrementAndGet());

        // Create the UIConfiguration
        UIConfigurationDTO uIConfigurationDTO = uIConfigurationMapper.toDto(uIConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUIConfigurationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(uIConfigurationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UIConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUIConfiguration() throws Exception {
        // Initialize the database
        insertedUIConfiguration = uIConfigurationRepository.saveAndFlush(uIConfiguration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the uIConfiguration
        restUIConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, uIConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return uIConfigurationRepository.count();
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

    protected UIConfiguration getPersistedUIConfiguration(UIConfiguration uIConfiguration) {
        return uIConfigurationRepository.findById(uIConfiguration.getId()).orElseThrow();
    }

    protected void assertPersistedUIConfigurationToMatchAllProperties(UIConfiguration expectedUIConfiguration) {
        assertUIConfigurationAllPropertiesEquals(expectedUIConfiguration, getPersistedUIConfiguration(expectedUIConfiguration));
    }

    protected void assertPersistedUIConfigurationToMatchUpdatableProperties(UIConfiguration expectedUIConfiguration) {
        assertUIConfigurationAllUpdatablePropertiesEquals(expectedUIConfiguration, getPersistedUIConfiguration(expectedUIConfiguration));
    }
}
