package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.Paiement;
import org.jhipster.projetintern.service.dto.PaiementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paiement} and its DTO {@link PaiementDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaiementMapper extends EntityMapper<PaiementDTO, Paiement> {}
