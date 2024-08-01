package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.EmailTemplateConfiguration;
import org.jhipster.projetintern.domain.Hotel;
import org.jhipster.projetintern.service.dto.EmailTemplateConfigurationDTO;
import org.jhipster.projetintern.service.dto.HotelDTO;
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
