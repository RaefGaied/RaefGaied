package org.jhipster.projetintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.projetintern.domain.enumeration.Statut;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_debut")
    private ZonedDateTime dateDebut;

    @Column(name = "date_fin")
    private ZonedDateTime dateFin;

    @Column(name = "total_prix")
    private Float totalPrix;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_paiement")
    private Statut statutPaiement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partenaires", "hotel", "partenaire", "reservation" }, allowSetters = true)
    private Set<Service> services = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "services", "uiConfigurations", "emailConfigs", "authConfigs", "hotelAdministrateur" },
        allowSetters = true
    )
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateDebut() {
        return this.dateDebut;
    }

    public Reservation dateDebut(ZonedDateTime dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(ZonedDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public ZonedDateTime getDateFin() {
        return this.dateFin;
    }

    public Reservation dateFin(ZonedDateTime dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(ZonedDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Float getTotalPrix() {
        return this.totalPrix;
    }

    public Reservation totalPrix(Float totalPrix) {
        this.setTotalPrix(totalPrix);
        return this;
    }

    public void setTotalPrix(Float totalPrix) {
        this.totalPrix = totalPrix;
    }

    public Statut getStatutPaiement() {
        return this.statutPaiement;
    }

    public Reservation statutPaiement(Statut statutPaiement) {
        this.setStatutPaiement(statutPaiement);
        return this;
    }

    public void setStatutPaiement(Statut statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        if (this.services != null) {
            this.services.forEach(i -> i.setReservation(null));
        }
        if (services != null) {
            services.forEach(i -> i.setReservation(this));
        }
        this.services = services;
    }

    public Reservation services(Set<Service> services) {
        this.setServices(services);
        return this;
    }

    public Reservation addService(Service service) {
        this.services.add(service);
        service.setReservation(this);
        return this;
    }

    public Reservation removeService(Service service) {
        this.services.remove(service);
        service.setReservation(null);
        return this;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Reservation hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return getId() != null && getId().equals(((Reservation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", totalPrix=" + getTotalPrix() +
            ", statutPaiement='" + getStatutPaiement() + "'" +
            "}";
    }
}
