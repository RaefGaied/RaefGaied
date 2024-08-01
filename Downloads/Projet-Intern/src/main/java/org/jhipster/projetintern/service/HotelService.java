package org.jhipster.projetintern.service;

import java.util.Optional;
import org.jhipster.projetintern.domain.Hotel;
import org.jhipster.projetintern.repository.HotelRepository;
import org.jhipster.projetintern.service.dto.HotelDTO;
import org.jhipster.projetintern.service.mapper.HotelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.jhipster.projetintern.domain.Hotel}.
 */
@Service
@Transactional
public class HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelService.class);

    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    /**
     * Save a hotel.
     *
     * @param hotelDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelDTO save(HotelDTO hotelDTO) {
        log.debug("Request to save Hotel : {}", hotelDTO);
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    /**
     * Update a hotel.
     *
     * @param hotelDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelDTO update(HotelDTO hotelDTO) {
        log.debug("Request to update Hotel : {}", hotelDTO);
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.toDto(hotel);
    }

    /**
     * Partially update a hotel.
     *
     * @param hotelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelDTO> partialUpdate(HotelDTO hotelDTO) {
        log.debug("Request to partially update Hotel : {}", hotelDTO);

        return hotelRepository
            .findById(hotelDTO.getId())
            .map(existingHotel -> {
                hotelMapper.partialUpdate(existingHotel, hotelDTO);

                return existingHotel;
            })
            .map(hotelRepository::save)
            .map(hotelMapper::toDto);
    }

    /**
     * Get all the hotels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HotelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Hotels");
        return hotelRepository.findAll(pageable).map(hotelMapper::toDto);
    }

    /**
     * Get one hotel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelDTO> findOne(Long id) {
        log.debug("Request to get Hotel : {}", id);
        return hotelRepository.findById(id).map(hotelMapper::toDto);
    }

    /**
     * Delete the hotel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hotel : {}", id);
        hotelRepository.deleteById(id);
    }
}
