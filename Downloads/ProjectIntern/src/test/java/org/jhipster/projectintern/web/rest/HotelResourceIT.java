package org.jhipster.projectintern.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.projectintern.domain.HotelAsserts.*;
import static org.jhipster.projectintern.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.projectintern.IntegrationTest;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.repository.HotelRepository;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.mapper.HotelMapper;
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
 * Integration tests for the {@link HotelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HotelResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_TEL = "AAAAAAAAAA";
    private static final String UPDATED_NUM_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_VUE_S = "AAAAAAAAAA";
    private static final String UPDATED_VUE_S = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final String DEFAULT_NOTATION = "AAAAAAAAAA";
    private static final String UPDATED_NOTATION = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_UNIQUE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_UNIQUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hotels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHotelMockMvc;

    private Hotel hotel;

    private Hotel insertedHotel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .nom(DEFAULT_NOM)
            .address(DEFAULT_ADDRESS)
            .numTel(DEFAULT_NUM_TEL)
            .pays(DEFAULT_PAYS)
            .ville(DEFAULT_VILLE)
            .vueS(DEFAULT_VUE_S)
            .capacity(DEFAULT_CAPACITY)
            .notation(DEFAULT_NOTATION)
            .lienUnique(DEFAULT_LIEN_UNIQUE);
        return hotel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hotel createUpdatedEntity(EntityManager em) {
        Hotel hotel = new Hotel()
            .nom(UPDATED_NOM)
            .address(UPDATED_ADDRESS)
            .numTel(UPDATED_NUM_TEL)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .vueS(UPDATED_VUE_S)
            .capacity(UPDATED_CAPACITY)
            .notation(UPDATED_NOTATION)
            .lienUnique(UPDATED_LIEN_UNIQUE);
        return hotel;
    }

    @BeforeEach
    public void initTest() {
        hotel = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedHotel != null) {
            hotelRepository.delete(insertedHotel);
            insertedHotel = null;
        }
    }

    @Test
    @Transactional
    void createHotel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);
        var returnedHotelDTO = om.readValue(
            restHotelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HotelDTO.class
        );

        // Validate the Hotel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHotel = hotelMapper.toEntity(returnedHotelDTO);
        assertHotelUpdatableFieldsEquals(returnedHotel, getPersistedHotel(returnedHotel));

        insertedHotel = returnedHotel;
    }

    @Test
    @Transactional
    void createHotelWithExistingId() throws Exception {
        // Create the Hotel with an existing ID
        hotel.setId(1L);
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHotelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHotels() throws Exception {
        // Initialize the database
        insertedHotel = hotelRepository.saveAndFlush(hotel);

        // Get all the hotelList
        restHotelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hotel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].numTel").value(hasItem(DEFAULT_NUM_TEL)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].vueS").value(hasItem(DEFAULT_VUE_S)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION)))
            .andExpect(jsonPath("$.[*].lienUnique").value(hasItem(DEFAULT_LIEN_UNIQUE)));
    }

    @Test
    @Transactional
    void getHotel() throws Exception {
        // Initialize the database
        insertedHotel = hotelRepository.saveAndFlush(hotel);

        // Get the hotel
        restHotelMockMvc
            .perform(get(ENTITY_API_URL_ID, hotel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hotel.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.numTel").value(DEFAULT_NUM_TEL))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.vueS").value(DEFAULT_VUE_S))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.notation").value(DEFAULT_NOTATION))
            .andExpect(jsonPath("$.lienUnique").value(DEFAULT_LIEN_UNIQUE));
    }

    @Test
    @Transactional
    void getNonExistingHotel() throws Exception {
        // Get the hotel
        restHotelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHotel() throws Exception {
        // Initialize the database
        insertedHotel = hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotel
        Hotel updatedHotel = hotelRepository.findById(hotel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHotel are not directly saved in db
        em.detach(updatedHotel);
        updatedHotel
            .nom(UPDATED_NOM)
            .address(UPDATED_ADDRESS)
            .numTel(UPDATED_NUM_TEL)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .vueS(UPDATED_VUE_S)
            .capacity(UPDATED_CAPACITY)
            .notation(UPDATED_NOTATION)
            .lienUnique(UPDATED_LIEN_UNIQUE);
        HotelDTO hotelDTO = hotelMapper.toDto(updatedHotel);

        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHotelToMatchAllProperties(updatedHotel);
    }

    @Test
    @Transactional
    void putNonExistingHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hotelDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hotelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHotelWithPatch() throws Exception {
        // Initialize the database
        insertedHotel = hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotel using partial update
        Hotel partialUpdatedHotel = new Hotel();
        partialUpdatedHotel.setId(hotel.getId());

        partialUpdatedHotel.address(UPDATED_ADDRESS).notation(UPDATED_NOTATION);

        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedHotel, hotel), getPersistedHotel(hotel));
    }

    @Test
    @Transactional
    void fullUpdateHotelWithPatch() throws Exception {
        // Initialize the database
        insertedHotel = hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hotel using partial update
        Hotel partialUpdatedHotel = new Hotel();
        partialUpdatedHotel.setId(hotel.getId());

        partialUpdatedHotel
            .nom(UPDATED_NOM)
            .address(UPDATED_ADDRESS)
            .numTel(UPDATED_NUM_TEL)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .vueS(UPDATED_VUE_S)
            .capacity(UPDATED_CAPACITY)
            .notation(UPDATED_NOTATION)
            .lienUnique(UPDATED_LIEN_UNIQUE);

        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHotel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHotel))
            )
            .andExpect(status().isOk());

        // Validate the Hotel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHotelUpdatableFieldsEquals(partialUpdatedHotel, getPersistedHotel(partialUpdatedHotel));
    }

    @Test
    @Transactional
    void patchNonExistingHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hotelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hotelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHotel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hotel.setId(longCount.incrementAndGet());

        // Create the Hotel
        HotelDTO hotelDTO = hotelMapper.toDto(hotel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHotelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hotelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hotel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHotel() throws Exception {
        // Initialize the database
        insertedHotel = hotelRepository.saveAndFlush(hotel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hotel
        restHotelMockMvc
            .perform(delete(ENTITY_API_URL_ID, hotel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hotelRepository.count();
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

    protected Hotel getPersistedHotel(Hotel hotel) {
        return hotelRepository.findById(hotel.getId()).orElseThrow();
    }

    protected void assertPersistedHotelToMatchAllProperties(Hotel expectedHotel) {
        assertHotelAllPropertiesEquals(expectedHotel, getPersistedHotel(expectedHotel));
    }

    protected void assertPersistedHotelToMatchUpdatableProperties(Hotel expectedHotel) {
        assertHotelAllUpdatablePropertiesEquals(expectedHotel, getPersistedHotel(expectedHotel));
    }
}
