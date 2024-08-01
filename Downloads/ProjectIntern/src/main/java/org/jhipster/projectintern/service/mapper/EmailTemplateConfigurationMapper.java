package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.EmailTemplateConfiguration;
import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailTemplateConfiguration} and its DTO {@link EmailTemplateConfigurationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmailTemplateConfigurationMapper extends EntityMapper<EmailTemplateConfigurationDTO, EmailTemplateConfiguration> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    EmailTemplateConfigurationDTO toDto(EmailTemplateConfiguration s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelDTO toDtoHotelId(Hotel hotel);
}
