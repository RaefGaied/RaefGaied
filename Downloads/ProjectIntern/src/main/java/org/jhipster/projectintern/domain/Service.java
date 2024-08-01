package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Service.
 */
@Entity
@Table(name = "service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "prix")
    private Float prix;

    @Column(name = "disposability")
    private String disposability;

    @Column(name = "capacite")
    private Integer capacite;

    @Column(name = "type_service")
    private String typeService;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "services", "service" }, allowSetters = true)
    private Set<Partenaire> partenaires = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "services", "uiConfigurations", "emailConfigs", "authConfigs", "hotelAdministrateur" },
        allowSetters = true
    )
    private Hotel hotel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
    @ManyToOne(fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "services", "service" }, allowSetters = true)
    private Partenaire partenaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "services", "hotel", "user" }, allowSetters = true)
    private Reservation reservation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Service id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Service nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Service description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrix() {
        return this.prix;
    }

    public Service prix(Float prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getDisposability() {
        return this.disposability;
    }

    public Service disposability(String disposability) {
        this.setDisposability(disposability);
        return this;
    }

    public void setDisposability(String disposability) {
        this.disposability = disposability;
    }

    public Integer getCapacite() {
        return this.capacite;
    }

    public Service capacite(Integer capacite) {
        this.setCapacite(capacite);
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getTypeService() {
        return this.typeService;
    }

    public Service typeService(String typeService) {
        this.setTypeService(typeService);
        return this;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public Set<Partenaire> getPartenaires() {
        return this.partenaires;
    }

    public void setPartenaires(Set<Partenaire> partenaires) {
        if (this.partenaires != null) {
            this.partenaires.forEach(i -> i.setService(null));
        }
        if (partenaires != null) {
            partenaires.forEach(i -> i.setService(this));
        }
        this.partenaires = partenaires;
    }

    public Service partenaires(Set<Partenaire> partenaires) {
        this.setPartenaires(partenaires);
        return this;
    }

    public Service addPartenaire(Partenaire partenaire) {
        this.partenaires.add(partenaire);
        partenaire.setService(this);
        return this;
    }

    public Service removePartenaire(Partenaire partenaire) {
        this.partenaires.remove(partenaire);
        partenaire.setService(null);
        return this;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Service hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    public Partenaire getPartenaire() {
        return this.partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public Service partenaire(Partenaire partenaire) {
        this.setPartenaire(partenaire);
        return this;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Service reservation(Reservation reservation) {
        this.setReservation(reservation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Service)) {
            return false;
        }
        return getId() != null && getId().equals(((Service) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Service{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", prix=" + getPrix() +
            ", disposability='" + getDisposability() + "'" +
            ", capacite=" + getCapacite() +
            ", typeService='" + getTypeService() + "'" +
            "}";
    }
}
