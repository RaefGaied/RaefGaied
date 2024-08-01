package org.jhipster.projetintern.service.mapper;

import org.jhipster.projetintern.domain.Hotel;
import org.jhipster.projetintern.domain.Reservation;
import org.jhipster.projetintern.domain.User;
import org.jhipster.projetintern.service.dto.HotelDTO;
import org.jhipster.projetintern.service.dto.ReservationDTO;
import org.jhipster.projetintern.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Reservation} and its DTO {@link ReservationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReservationMapper extends EntityMapper<ReservationDTO, Reservation> {
    @Mapping(target = "hotel", source = "hotel", qualifiedByName = "hotelId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    ReservationDTO toDto(Reservation s);

    @Named("hotelId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    HotelDTO toDtoHotelId(Hotel hotel);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
