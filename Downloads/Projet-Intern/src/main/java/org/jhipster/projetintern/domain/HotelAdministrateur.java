package org.jhipster.projetintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HotelAdministrateur.
 */
@Entity
@Table(name = "hotel_administrateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HotelAdministrateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    @Column(name = "mot_de_passe")
    private String motDePasse;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotelAdministrateur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "services", "uiConfigurations", "emailConfigs", "authConfigs", "hotelAdministrateur" },
        allowSetters = true
    )
    private Set<Hotel> hotels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HotelAdministrateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public HotelAdministrateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public HotelAdministrateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public HotelAdministrateur motDePasse(String motDePasse) {
        this.setMotDePasse(motDePasse);
        return this;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Set<Hotel> getHotels() {
        return this.hotels;
    }

    public void setHotels(Set<Hotel> hotels) {
        if (this.hotels != null) {
            this.hotels.forEach(i -> i.setHotelAdministrateur(null));
        }
        if (hotels != null) {
            hotels.forEach(i -> i.setHotelAdministrateur(this));
        }
        this.hotels = hotels;
    }

    public HotelAdministrateur hotels(Set<Hotel> hotels) {
        this.setHotels(hotels);
        return this;
    }

    public HotelAdministrateur addHotels(Hotel hotel) {
        this.hotels.add(hotel);
        hotel.setHotelAdministrateur(this);
        return this;
    }

    public HotelAdministrateur removeHotels(Hotel hotel) {
        this.hotels.remove(hotel);
        hotel.setHotelAdministrateur(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HotelAdministrateur)) {
            return false;
        }
        return getId() != null && getId().equals(((HotelAdministrateur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HotelAdministrateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            "}";
    }
}
