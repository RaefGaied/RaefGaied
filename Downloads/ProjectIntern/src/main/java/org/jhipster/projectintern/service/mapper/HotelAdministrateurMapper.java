package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.HotelAdministrateur;
import org.jhipster.projectintern.service.dto.HotelAdministrateurDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HotelAdministrateur} and its DTO {@link HotelAdministrateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelAdministrateurMapper extends EntityMapper<HotelAdministrateurDTO, HotelAdministrateur> {}
