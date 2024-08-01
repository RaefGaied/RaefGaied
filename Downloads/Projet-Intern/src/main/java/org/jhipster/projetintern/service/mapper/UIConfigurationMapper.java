package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.Hotel;
import org.jhipster.projetintern.domain.UIConfiguration;
import org.jhipster.projetintern.service.dto.HotelDTO;
import org.jhipster.projetintern.service.dto.UIConfigurationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UIConfiguration} and its DTO {@link UIConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface UIConfigurationMapper extends EntityMapper<UIConfigurationDTO, UIConfiguration> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    UIConfigurationDTO toDto(UIConfiguration s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelDTO toDtoHotelId(Hotel hotel);
}
