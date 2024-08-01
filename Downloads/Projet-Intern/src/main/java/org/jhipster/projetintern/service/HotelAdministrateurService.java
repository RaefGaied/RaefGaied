package org.jhipster.projetintern.service;

import java.util.Optional;
import org.jhipster.projetintern.domain.HotelAdministrateur;
import org.jhipster.projetintern.repository.HotelAdministrateurRepository;
import org.jhipster.projetintern.service.dto.HotelAdministrateurDTO;
import org.jhipster.projetintern.service.mapper.HotelAdministrateurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.jhipster.projetintern.domain.HotelAdministrateur}.
 */
@Service
@Transactional
public class HotelAdministrateurService {

    private static final Logger log = LoggerFactory.getLogger(HotelAdministrateurService.class);

    private final HotelAdministrateurRepository hotelAdministrateurRepository;

    private final HotelAdministrateurMapper hotelAdministrateurMapper;

    public HotelAdministrateurService(
        HotelAdministrateurRepository hotelAdministrateurRepository,
        HotelAdministrateurMapper hotelAdministrateurMapper
    ) {
        this.hotelAdministrateurRepository = hotelAdministrateurRepository;
        this.hotelAdministrateurMapper = hotelAdministrateurMapper;
    }

    /**
     * Save a hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelAdministrateurDTO save(HotelAdministrateurDTO hotelAdministrateurDTO) {
        log.debug("Request to save HotelAdministrateur : {}", hotelAdministrateurDTO);
        HotelAdministrateur hotelAdministrateur = hotelAdministrateurMapper.toEntity(hotelAdministrateurDTO);
        hotelAdministrateur = hotelAdministrateurRepository.save(hotelAdministrateur);
        return hotelAdministrateurMapper.toDto(hotelAdministrateur);
    }

    /**
     * Update a hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the entity to save.
     * @return the persisted entity.
     */
    public HotelAdministrateurDTO update(HotelAdministrateurDTO hotelAdministrateurDTO) {
        log.debug("Request to update HotelAdministrateur : {}", hotelAdministrateurDTO);
        HotelAdministrateur hotelAdministrateur = hotelAdministrateurMapper.toEntity(hotelAdministrateurDTO);
        hotelAdministrateur = hotelAdministrateurRepository.save(hotelAdministrateur);
        return hotelAdministrateurMapper.toDto(hotelAdministrateur);
    }

    /**
     * Partially update a hotelAdministrateur.
     *
     * @param hotelAdministrateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HotelAdministrateurDTO> partialUpdate(HotelAdministrateurDTO hotelAdministrateurDTO) {
        log.debug("Request to partially update HotelAdministrateur : {}", hotelAdministrateurDTO);

        return hotelAdministrateurRepository
            .findById(hotelAdministrateurDTO.getId())
            .map(existingHotelAdministrateur -> {
                hotelAdministrateurMapper.partialUpdate(existingHotelAdministrateur, hotelAdministrateurDTO);

                return existingHotelAdministrateur;
            })
            .map(hotelAdministrateurRepository::save)
            .map(hotelAdministrateurMapper::toDto);
    }

    /**
     * Get all the hotelAdministrateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HotelAdministrateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HotelAdministrateurs");
        return hotelAdministrateurRepository.findAll(pageable).map(hotelAdministrateurMapper::toDto);
    }

    /**
     * Get one hotelAdministrateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HotelAdministrateurDTO> findOne(Long id) {
        log.debug("Request to get HotelAdministrateur : {}", id);
        return hotelAdministrateurRepository.findById(id).map(hotelAdministrateurMapper::toDto);
    }

    /**
     * Delete the hotelAdministrateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HotelAdministrateur : {}", id);
        hotelAdministrateurRepository.deleteById(id);
    }
}
