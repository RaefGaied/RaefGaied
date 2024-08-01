package org.jhipster.projetintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projetintern.domain.EmailTemplateConfigurationAsserts.*;
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
import org.jhipster.projetintern.domain.EmailTemplateConfiguration;
import org.jhipster.projetintern.domain.enumeration.Act;
import org.jhipster.projetintern.repository.EmailTemplateConfigurationRepository;
import org.jhipster.projetintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projetintern.service.mapper.EmailTemplateConfigurationMapper;
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
 * Integration tests for the {@link EmailTemplateConfigurationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmailTemplateConfigurationResourceIT {

    private static final String DEFAULT_NOM_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_CORPS = "AAAAAAAAAA";
    private static final String UPDATED_CORPS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIFY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIFY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Act DEFAULT_ACTIVE_STATUS = Act.AVAILABLE;
    private static final Act UPDATED_ACTIVE_STATUS = Act.OFFLINE;

    private static final String ENTITY_API_URL = "/api/email-template-configurations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EmailTemplateConfigurationRepository emailTemplateConfigurationRepository;

    @Autowired
    private EmailTemplateConfigurationMapper emailTemplateConfigurationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailTemplateConfigurationMockMvc;

    private EmailTemplateConfiguration emailTemplateConfiguration;

    private EmailTemplateConfiguration insertedEmailTemplateConfiguration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTemplateConfiguration createEntity(EntityManager em) {
        EmailTemplateConfiguration emailTemplateConfiguration = new EmailTemplateConfiguration()
            .nomTemplate(DEFAULT_NOM_TEMPLATE)
            .corps(DEFAULT_CORPS)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateModify(DEFAULT_DATE_MODIFY)
            .activeStatus(DEFAULT_ACTIVE_STATUS);
        return emailTemplateConfiguration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTemplateConfiguration createUpdatedEntity(EntityManager em) {
        EmailTemplateConfiguration emailTemplateConfiguration = new EmailTemplateConfiguration()
            .nomTemplate(UPDATED_NOM_TEMPLATE)
            .corps(UPDATED_CORPS)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY)
            .activeStatus(UPDATED_ACTIVE_STATUS);
        return emailTemplateConfiguration;
    }

    @BeforeEach
    public void initTest() {
        emailTemplateConfiguration = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEmailTemplateConfiguration != null) {
            emailTemplateConfigurationRepository.delete(insertedEmailTemplateConfiguration);
            insertedEmailTemplateConfiguration = null;
        }
    }

    @Test
    @Transactional
    void createEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);
        var returnedEmailTemplateConfigurationDTO = om.readValue(
            restEmailTemplateConfigurationMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EmailTemplateConfigurationDTO.class
        );

        // Validate the EmailTemplateConfiguration in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEmailTemplateConfiguration = emailTemplateConfigurationMapper.toEntity(returnedEmailTemplateConfigurationDTO);
        assertEmailTemplateConfigurationUpdatableFieldsEquals(
            returnedEmailTemplateConfiguration,
            getPersistedEmailTemplateConfiguration(returnedEmailTemplateConfiguration)
        );

        insertedEmailTemplateConfiguration = returnedEmailTemplateConfiguration;
    }

    @Test
    @Transactional
    void createEmailTemplateConfigurationWithExistingId() throws Exception {
        // Create the EmailTemplateConfiguration with an existing ID
        emailTemplateConfiguration.setId(1L);
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTemplateConfigurationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmailTemplateConfigurations() throws Exception {
        // Initialize the database
        insertedEmailTemplateConfiguration = emailTemplateConfigurationRepository.saveAndFlush(emailTemplateConfiguration);

        // Get all the emailTemplateConfigurationList
        restEmailTemplateConfigurationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTemplateConfiguration.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomTemplate").value(hasItem(DEFAULT_NOM_TEMPLATE)))
            .andExpect(jsonPath("$.[*].corps").value(hasItem(DEFAULT_CORPS)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(sameInstant(DEFAULT_DATE_CREATION))))
            .andExpect(jsonPath("$.[*].dateModify").value(hasItem(sameInstant(DEFAULT_DATE_MODIFY))))
            .andExpect(jsonPath("$.[*].activeStatus").value(hasItem(DEFAULT_ACTIVE_STATUS.toString())));
    }

    @Test
    @Transactional
    void getEmailTemplateConfiguration() throws Exception {
        // Initialize the database
        insertedEmailTemplateConfiguration = emailTemplateConfigurationRepository.saveAndFlush(emailTemplateConfiguration);

        // Get the emailTemplateConfiguration
        restEmailTemplateConfigurationMockMvc
            .perform(get(ENTITY_API_URL_ID, emailTemplateConfiguration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailTemplateConfiguration.getId().intValue()))
            .andExpect(jsonPath("$.nomTemplate").value(DEFAULT_NOM_TEMPLATE))
            .andExpect(jsonPath("$.corps").value(DEFAULT_CORPS))
            .andExpect(jsonPath("$.dateCreation").value(sameInstant(DEFAULT_DATE_CREATION)))
            .andExpect(jsonPath("$.dateModify").value(sameInstant(DEFAULT_DATE_MODIFY)))
            .andExpect(jsonPath("$.activeStatus").value(DEFAULT_ACTIVE_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEmailTemplateConfiguration() throws Exception {
        // Get the emailTemplateConfiguration
        restEmailTemplateConfigurationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmailTemplateConfiguration() throws Exception {
        // Initialize the database
        insertedEmailTemplateConfiguration = emailTemplateConfigurationRepository.saveAndFlush(emailTemplateConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the emailTemplateConfiguration
        EmailTemplateConfiguration updatedEmailTemplateConfiguration = emailTemplateConfigurationRepository
            .findById(emailTemplateConfiguration.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEmailTemplateConfiguration are not directly saved in db
        em.detach(updatedEmailTemplateConfiguration);
        updatedEmailTemplateConfiguration
            .nomTemplate(UPDATED_NOM_TEMPLATE)
            .corps(UPDATED_CORPS)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY)
            .activeStatus(UPDATED_ACTIVE_STATUS);
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(
            updatedEmailTemplateConfiguration
        );

        restEmailTemplateConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailTemplateConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEmailTemplateConfigurationToMatchAllProperties(updatedEmailTemplateConfiguration);
    }

    @Test
    @Transactional
    void putNonExistingEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        emailTemplateConfiguration.setId(longCount.incrementAndGet());

        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTemplateConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emailTemplateConfigurationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        emailTemplateConfiguration.setId(longCount.incrementAndGet());

        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailTemplateConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        emailTemplateConfiguration.setId(longCount.incrementAndGet());

        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailTemplateConfigurationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmailTemplateConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedEmailTemplateConfiguration = emailTemplateConfigurationRepository.saveAndFlush(emailTemplateConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the emailTemplateConfiguration using partial update
        EmailTemplateConfiguration partialUpdatedEmailTemplateConfiguration = new EmailTemplateConfiguration();
        partialUpdatedEmailTemplateConfiguration.setId(emailTemplateConfiguration.getId());

        partialUpdatedEmailTemplateConfiguration
            .nomTemplate(UPDATED_NOM_TEMPLATE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY)
            .activeStatus(UPDATED_ACTIVE_STATUS);

        restEmailTemplateConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailTemplateConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmailTemplateConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the EmailTemplateConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailTemplateConfigurationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEmailTemplateConfiguration, emailTemplateConfiguration),
            getPersistedEmailTemplateConfiguration(emailTemplateConfiguration)
        );
    }

    @Test
    @Transactional
    void fullUpdateEmailTemplateConfigurationWithPatch() throws Exception {
        // Initialize the database
        insertedEmailTemplateConfiguration = emailTemplateConfigurationRepository.saveAndFlush(emailTemplateConfiguration);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the emailTemplateConfiguration using partial update
        EmailTemplateConfiguration partialUpdatedEmailTemplateConfiguration = new EmailTemplateConfiguration();
        partialUpdatedEmailTemplateConfiguration.setId(emailTemplateConfiguration.getId());

        partialUpdatedEmailTemplateConfiguration
            .nomTemplate(UPDATED_NOM_TEMPLATE)
            .corps(UPDATED_CORPS)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateModify(UPDATED_DATE_MODIFY)
            .activeStatus(UPDATED_ACTIVE_STATUS);

        restEmailTemplateConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmailTemplateConfiguration.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEmailTemplateConfiguration))
            )
            .andExpect(status().isOk());

        // Validate the EmailTemplateConfiguration in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEmailTemplateConfigurationUpdatableFieldsEquals(
            partialUpdatedEmailTemplateConfiguration,
            getPersistedEmailTemplateConfiguration(partialUpdatedEmailTemplateConfiguration)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        emailTemplateConfiguration.setId(longCount.incrementAndGet());

        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTemplateConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emailTemplateConfigurationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        emailTemplateConfiguration.setId(longCount.incrementAndGet());

        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailTemplateConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmailTemplateConfiguration() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        emailTemplateConfiguration.setId(longCount.incrementAndGet());

        // Create the EmailTemplateConfiguration
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = emailTemplateConfigurationMapper.toDto(emailTemplateConfiguration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmailTemplateConfigurationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(emailTemplateConfigurationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmailTemplateConfiguration in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmailTemplateConfiguration() throws Exception {
        // Initialize the database
        insertedEmailTemplateConfiguration = emailTemplateConfigurationRepository.saveAndFlush(emailTemplateConfiguration);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the emailTemplateConfiguration
        restEmailTemplateConfigurationMockMvc
            .perform(delete(ENTITY_API_URL_ID, emailTemplateConfiguration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return emailTemplateConfigurationRepository.count();
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

    protected EmailTemplateConfiguration getPersistedEmailTemplateConfiguration(EmailTemplateConfiguration emailTemplateConfiguration) {
        return emailTemplateConfigurationRepository.findById(emailTemplateConfiguration.getId()).orElseThrow();
    }

    protected void assertPersistedEmailTemplateConfigurationToMatchAllProperties(
        EmailTemplateConfiguration expectedEmailTemplateConfiguration
    ) {
        assertEmailTemplateConfigurationAllPropertiesEquals(
            expectedEmailTemplateConfiguration,
            getPersistedEmailTemplateConfiguration(expectedEmailTemplateConfiguration)
        );
    }

    protected void assertPersistedEmailTemplateConfigurationToMatchUpdatableProperties(
        EmailTemplateConfiguration expectedEmailTemplateConfiguration
    ) {
        assertEmailTemplateConfigurationAllUpdatablePropertiesEquals(
            expectedEmailTemplateConfiguration,
            getPersistedEmailTemplateConfiguration(expectedEmailTemplateConfiguration)
        );
    }
}
