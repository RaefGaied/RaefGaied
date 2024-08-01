package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.Hotel;
import org.jhipster.projetintern.domain.HotelAdministrateur;
import org.jhipster.projetintern.service.dto.HotelAdministrateurDTO;
import org.jhipster.projetintern.service.dto.HotelDTO;
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
