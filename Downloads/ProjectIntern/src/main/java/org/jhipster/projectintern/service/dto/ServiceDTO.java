package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.Service} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceDTO implements Serializable {

    private Long id;

    private String nom;

    private String description;

    private Float prix;

    private String disposability;

    private Integer capacite;

    private String typeService;

    private HotelDTO hotel;

    private PartenaireDTO partenaire;

    private ReservationDTO reservation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getDisposability() {
        return disposability;
    }

    public void setDisposability(String disposability) {
        this.disposability = disposability;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public PartenaireDTO getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(PartenaireDTO partenaire) {
        this.partenaire = partenaire;
    }

    public ReservationDTO getReservation() {
        return reservation;
    }

    public void setReservation(ReservationDTO reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceDTO)) {
            return false;
        }

        ServiceDTO serviceDTO = (ServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, serviceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", prix=" + getPrix() +
            ", disposability='" + getDisposability() + "'" +
            ", capacite=" + getCapacite() +
            ", typeService='" + getTypeService() + "'" +
            ", hotel=" + getHotel() +
            ", partenaire=" + getPartenaire() +
            ", reservation=" + getReservation() +
            "}";
    }
}
