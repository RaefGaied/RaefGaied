package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.HotelAdministrateur;
import org.jhipster.projectintern.service.dto.HotelAdministrateurDTO;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hotel} and its DTO {@link HotelDTO}.
 */
@Mapper(componentModel = "spring")
public interface HotelMapper extends EntityMapper<HotelDTO, Hotel> {
    @Mapping(target = "hotelAdministrateur", source = "hotelAdministrateur", qualifiedByName = "hotelAdministrateurId")
    HotelDTO toDto(Hotel s);

    @Named("hotelAdministrateurId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelAdministrateurDTO toDtoHotelAdministrateurId(HotelAdministrateur hotelAdministrateur);
}
