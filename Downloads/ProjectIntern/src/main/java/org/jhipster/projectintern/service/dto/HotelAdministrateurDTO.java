package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.HotelAdministrateur} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelAdministrateurDTO implements Serializable {

    private Long id;

    private String nom;

    private String email;

    private String motDePasse;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelAdministrateurDTO)) {
            return false;
        }

        HotelAdministrateurDTO hotelAdministrateurDTO = (HotelAdministrateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelAdministrateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelAdministrateurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            "}";
    }
}
