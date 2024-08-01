package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.Partenaire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PartenaireDTO implements Serializable {

    private Long id;

    private String description;

    private String nom;

    private String contact;

    private String adresse;

    private String typePartenaire;

    private ServiceDTO service;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTypePartenaire() {
        return typePartenaire;
    }

    public void setTypePartenaire(String typePartenaire) {
        this.typePartenaire = typePartenaire;
    }

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartenaireDTO)) {
            return false;
        }

        PartenaireDTO partenaireDTO = (PartenaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, partenaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartenaireDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", nom='" + getNom() + "'" +
            ", contact='" + getContact() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", typePartenaire='" + getTypePartenaire() + "'" +
            ", service=" + getService() +
            "}";
    }
}
