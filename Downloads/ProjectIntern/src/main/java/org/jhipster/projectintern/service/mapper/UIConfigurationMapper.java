package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.UIConfiguration;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.dto.UIConfigurationDTO;
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
