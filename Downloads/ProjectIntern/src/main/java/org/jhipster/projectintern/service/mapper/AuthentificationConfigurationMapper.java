package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.AuthentificationConfiguration;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.service.dto.AuthentificationConfigurationDTO;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuthentificationConfiguration} and its DTO {@link AuthentificationConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthentificationConfigurationMapper extends EntityMapper<AuthentificationConfigurationDTO, AuthentificationConfiguration> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    AuthentificationConfigurationDTO toDto(AuthentificationConfiguration s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelDTO toDtoHotelId(Hotel hotel);
}
