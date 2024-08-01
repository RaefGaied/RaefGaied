package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.Hotel;
import org.jhipster.projetintern.domain.Partenaire;
import org.jhipster.projetintern.domain.Reservation;
import org.jhipster.projetintern.domain.Service;
import org.jhipster.projetintern.service.dto.HotelDTO;
import org.jhipster.projetintern.service.dto.PartenaireDTO;
import org.jhipster.projetintern.service.dto.ReservationDTO;
import org.jhipster.projetintern.service.dto.ServiceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Service} and its DTO {@link ServiceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServiceMapper extends EntityMapper<ServiceDTO, Service> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    @Mapping(target = "partenaire", source = "partenaire", qualifiedByName = "partenaireId")
    @Mapping(target = "reservation", source = "reservation", qualifiedByName = "reservationId")
    ServiceDTO toDto(Service s);

    @Named("partenaireId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PartenaireDTO toDtoPartenaireId(Partenaire partenaire);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelDTO toDtoHotelId(Hotel hotel);

    @Named("reservationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReservationDTO toDtoReservationId(Reservation reservation);
}
