package org.jhipster.projetintern.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projetintern.domain.Hotel} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelDTO implements Serializable {

    private Long id;

    private String nom;

    private String address;

    private String numTel;

    private String pays;

    private String ville;

    private String vueS;

    private Integer capacity;

    private String notation;

    private String lienUnique;

    private HotelAdministrateurDTO hotelAdministrateur;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVueS() {
        return vueS;
    }

    public void setVueS(String vueS) {
        this.vueS = vueS;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getLienUnique() {
        return lienUnique;
    }

    public void setLienUnique(String lienUnique) {
        this.lienUnique = lienUnique;
    }

    public HotelAdministrateurDTO getHotelAdministrateur() {
        return hotelAdministrateur;
    }

    public void setHotelAdministrateur(HotelAdministrateurDTO hotelAdministrateur) {
        this.hotelAdministrateur = hotelAdministrateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelDTO)) {
            return false;
        }

        HotelDTO hotelDTO = (HotelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hotelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", address='" + getAddress() + "'" +
            ", numTel='" + getNumTel() + "'" +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", vueS='" + getVueS() + "'" +
            ", capacity=" + getCapacity() +
            ", notation='" + getNotation() + "'" +
            ", lienUnique='" + getLienUnique() + "'" +
            ", hotelAdministrateur=" + getHotelAdministrateur() +
            "}";
    }
}
