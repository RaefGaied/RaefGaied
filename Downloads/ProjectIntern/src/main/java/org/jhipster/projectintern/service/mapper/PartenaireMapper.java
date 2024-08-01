package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.domain.Service;
import org.jhipster.projectintern.service.dto.PartenaireDTO;
import org.jhipster.projectintern.service.dto.ServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partenaire} and its DTO {@link PartenaireDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartenaireMapper extends EntityMapper<PartenaireDTO, Partenaire> {
    @Mapping(target = "service", source = "service", qualifiedByName = "serviceId")
    PartenaireDTO toDto(Partenaire s);

    @Named("serviceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServiceDTO toDtoServiceId(Service service);
}
