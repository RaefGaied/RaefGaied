package org.jhipster.projectintern.service.mapper;

import org.jhipster.projectintern.domain.Hotel;
import org.jhipster.projectintern.domain.Partenaire;
import org.jhipster.projectintern.domain.Reservation;
import org.jhipster.projectintern.domain.Service;
import org.jhipster.projectintern.service.dto.HotelDTO;
import org.jhipster.projectintern.service.dto.PartenaireDTO;
import org.jhipster.projectintern.service.dto.ReservationDTO;
import org.jhipster.projectintern.service.dto.ServiceDTO;
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
