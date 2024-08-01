package org.jhipster.projectintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projectintern.domain.ServiceAsserts.*;
import static org.jhipster.projectintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.projectintern.IntegrationTest;
import org.jhipster.projectintern.domain.Service;
import org.jhipster.projectintern.repository.ServiceRepository;
import org.jhipster.projectintern.service.dto.ServiceDTO;
import org.jhipster.projectintern.service.mapper.ServiceMapper;
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
 * Integration tests for the {@link ServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ServiceResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRIX = 1F;
    private static final Float UPDATED_PRIX = 2F;

    private static final String DEFAULT_DISPOSABILITY = "AAAAAAAAAA";
    private static final String UPDATED_DISPOSABILITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITE = 1;
    private static final Integer UPDATED_CAPACITE = 2;

    private static final String DEFAULT_TYPE_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SERVICE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceMockMvc;

    private Service service;

    private Service insertedService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Service createEntity(EntityManager em) {
        Service service = new Service()
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .prix(DEFAULT_PRIX)
            .disposability(DEFAULT_DISPOSABILITY)
            .capacite(DEFAULT_CAPACITE)
            .typeService(DEFAULT_TYPE_SERVICE);
        return service;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Service createUpdatedEntity(EntityManager em) {
        Service service = new Service()
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .disposability(UPDATED_DISPOSABILITY)
            .capacite(UPDATED_CAPACITE)
            .typeService(UPDATED_TYPE_SERVICE);
        return service;
    }

    @BeforeEach
    public void initTest() {
        service = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedService != null) {
            serviceRepository.delete(insertedService);
            insertedService = null;
        }
    }

    @Test
    @Transactional
    void createService() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);
        var returnedServiceDTO = om.readValue(
            restServiceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ServiceDTO.class
        );

        // Validate the Service in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedService = serviceMapper.toEntity(returnedServiceDTO);
        assertServiceUpdatableFieldsEquals(returnedService, getPersistedService(returnedService));

        insertedService = returnedService;
    }

    @Test
    @Transactional
    void createServiceWithExistingId() throws Exception {
        // Create the Service with an existing ID
        service.setId(1L);
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllServices() throws Exception {
        // Initialize the database
        insertedService = serviceRepository.saveAndFlush(service);

        // Get all the serviceList
        restServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX.doubleValue())))
            .andExpect(jsonPath("$.[*].disposability").value(hasItem(DEFAULT_DISPOSABILITY)))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)))
            .andExpect(jsonPath("$.[*].typeService").value(hasItem(DEFAULT_TYPE_SERVICE)));
    }

    @Test
    @Transactional
    void getService() throws Exception {
        // Initialize the database
        insertedService = serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(service.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX.doubleValue()))
            .andExpect(jsonPath("$.disposability").value(DEFAULT_DISPOSABILITY))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE))
            .andExpect(jsonPath("$.typeService").value(DEFAULT_TYPE_SERVICE));
    }

    @Test
    @Transactional
    void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingService() throws Exception {
        // Initialize the database
        insertedService = serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the service
        Service updatedService = serviceRepository.findById(service.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedService are not directly saved in db
        em.detach(updatedService);
        updatedService
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .disposability(UPDATED_DISPOSABILITY)
            .capacite(UPDATED_CAPACITE)
            .typeService(UPDATED_TYPE_SERVICE);
        ServiceDTO serviceDTO = serviceMapper.toDto(updatedService);

        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedServiceToMatchAllProperties(updatedService);
    }

    @Test
    @Transactional
    void putNonExistingService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(longCount.incrementAndGet());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, serviceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(longCount.incrementAndGet());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(longCount.incrementAndGet());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(serviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServiceWithPatch() throws Exception {
        // Initialize the database
        insertedService = serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the service using partial update
        Service partialUpdatedService = new Service();
        partialUpdatedService.setId(service.getId());

        partialUpdatedService.capacite(UPDATED_CAPACITE);

        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedService.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedService, service), getPersistedService(service));
    }

    @Test
    @Transactional
    void fullUpdateServiceWithPatch() throws Exception {
        // Initialize the database
        insertedService = serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the service using partial update
        Service partialUpdatedService = new Service();
        partialUpdatedService.setId(service.getId());

        partialUpdatedService
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .disposability(UPDATED_DISPOSABILITY)
            .capacite(UPDATED_CAPACITE)
            .typeService(UPDATED_TYPE_SERVICE);

        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedService.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedService))
            )
            .andExpect(status().isOk());

        // Validate the Service in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertServiceUpdatableFieldsEquals(partialUpdatedService, getPersistedService(partialUpdatedService));
    }

    @Test
    @Transactional
    void patchNonExistingService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(longCount.incrementAndGet());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, serviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(longCount.incrementAndGet());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(serviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamService() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        service.setId(longCount.incrementAndGet());

        // Create the Service
        ServiceDTO serviceDTO = serviceMapper.toDto(service);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServiceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(serviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Service in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteService() throws Exception {
        // Initialize the database
        insertedService = serviceRepository.saveAndFlush(service);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the service
        restServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, service.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return serviceRepository.count();
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

    protected Service getPersistedService(Service service) {
        return serviceRepository.findById(service.getId()).orElseThrow();
    }

    protected void assertPersistedServiceToMatchAllProperties(Service expectedService) {
        assertServiceAllPropertiesEquals(expectedService, getPersistedService(expectedService));
    }

    protected void assertPersistedServiceToMatchUpdatableProperties(Service expectedService) {
        assertServiceAllUpdatablePropertiesEquals(expectedService, getPersistedService(expectedService));
    }
}
