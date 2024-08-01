package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.HotelAdministrateur;
import org.jhipster.projetintern.service.dto.HotelAdministrateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelAdministrateur} and its DTO {@link HotelAdministrateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelAdministrateurMapper extends EntityMapper<HotelAdministrateurDTO, HotelAdministrateur> {}
