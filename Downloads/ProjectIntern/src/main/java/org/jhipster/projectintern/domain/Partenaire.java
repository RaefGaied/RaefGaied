package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Partenaire.
 */
@Entity
@Table(name = "partenaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Partenaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "nom")
    private String nom;

    @Column(name = "contact")
    private String contact;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "type_partenaire")
    private String typePartenaire;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partenaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partenaire", "hotel", "reservation" }, allowSetters = true)
    private Set<Service> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Partenaire id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Partenaire description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return this.nom;
    }

    public Partenaire nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return this.contact;
    }

    public Partenaire contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Partenaire adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTypePartenaire() {
        return this.typePartenaire;
    }

    public Partenaire typePartenaire(String typePartenaire) {
        this.setTypePartenaire(typePartenaire);
        return this;
    }

    public void setTypePartenaire(String typePartenaire) {
        this.typePartenaire = typePartenaire;
    }

    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        if (this.services != null) {
            this.services.forEach(i -> i.setPartenaire(null));
        }
        if (services != null) {
            services.forEach(i -> i.setPartenaire(this));
        }
        this.services = services;
    }

    public Partenaire services(Set<Service> services) {
        this.setServices(services);
        return this;
    }

    public Partenaire addService(Service service) {
        this.services.add(service);
        service.setPartenaire(this);
        return this;
    }

    public Partenaire removeService(Service service) {
        this.services.remove(service);
        service.setPartenaire(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partenaire)) {
            return false;
        }
        return getId() != null && getId().equals(((Partenaire) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partenaire{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", nom='" + getNom() + "'" +
            ", contact='" + getContact() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", typePartenaire='" + getTypePartenaire() + "'" +
            "}";
    }
}
