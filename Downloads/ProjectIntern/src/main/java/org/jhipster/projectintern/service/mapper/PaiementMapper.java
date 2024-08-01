package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Paiement;
import org.jhipster.projectintern.service.dto.PaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {}
