package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.jhipster.projectintern.domain.enumeration.Statut;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.Reservation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReservationDTO implements Serializable {

    private Long id;

    private ZonedDateTime dateDebut;

    private ZonedDateTime dateFin;

    private Float totalPrix;

    private Statut statutPaiement;

    private HotelDTO hotel;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Float getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(Float totalPrix) {
        this.totalPrix = totalPrix;
    }

    public Statut getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(Statut statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationDTO)) {
            return false;
        }

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reservationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", totalPrix=" + getTotalPrix() +
            ", statutPaiement='" + getStatutPaiement() + "'" +
            ", hotel=" + getHotel() +
            ", user=" + getUser() +
            "}";
    }
}
