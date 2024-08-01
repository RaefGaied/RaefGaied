package org.jhipster.projetintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hotel.
 */
@Entity
@Table(name = "hotel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hotel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "address")
    private String address;

    @Column(name = "num_tel")
    private String numTel;

    @Column(name = "pays")
    private String pays;

    @Column(name = "ville")
    private String ville;

    @Column(name = "vue_s")
    private String vueS;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "notation")
    private String notation;

    @Column(name = "lien_unique")
    private String lienUnique;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "partenaires", "hotel", "partenaire", "reservation" }, allowSetters = true)
    private Set<Service> services = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    private Set<UIConfiguration> uiConfigurations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    private Set<EmailTemplateConfiguration> emailConfigs = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hotel" }, allowSetters = true)
    private Set<AuthentificationConfiguration> authConfigs = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hotels" }, allowSetters = true)
    private HotelAdministrateur hotelAdministrateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hotel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Hotel nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddress() {
        return this.address;
    }

    public Hotel address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumTel() {
        return this.numTel;
    }

    public Hotel numTel(String numTel) {
        this.setNumTel(numTel);
        return this;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getPays() {
        return this.pays;
    }

    public Hotel pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return this.ville;
    }

    public Hotel ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVueS() {
        return this.vueS;
    }

    public Hotel vueS(String vueS) {
        this.setVueS(vueS);
        return this;
    }

    public void setVueS(String vueS) {
        this.vueS = vueS;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Hotel capacity(Integer capacity) {
        this.setCapacity(capacity);
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getNotation() {
        return this.notation;
    }

    public Hotel notation(String notation) {
        this.setNotation(notation);
        return this;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public String getLienUnique() {
        return this.lienUnique;
    }

    public Hotel lienUnique(String lienUnique) {
        this.setLienUnique(lienUnique);
        return this;
    }

    public void setLienUnique(String lienUnique) {
        this.lienUnique = lienUnique;
    }

    public Set<Service> getServices() {
        return this.services;
    }

    public void setServices(Set<Service> services) {
        if (this.services != null) {
            this.services.forEach(i -> i.setHotel(null));
        }
        if (services != null) {
            services.forEach(i -> i.setHotel(this));
        }
        this.services = services;
    }

    public Hotel services(Set<Service> services) {
        this.setServices(services);
        return this;
    }

    public Hotel addServices(Service service) {
        this.services.add(service);
        service.setHotel(this);
        return this;
    }

    public Hotel removeServices(Service service) {
        this.services.remove(service);
        service.setHotel(null);
        return this;
    }

    public Set<UIConfiguration> getUiConfigurations() {
        return this.uiConfigurations;
    }

    public void setUiConfigurations(Set<UIConfiguration> uIConfigurations) {
        if (this.uiConfigurations != null) {
            this.uiConfigurations.forEach(i -> i.setHotel(null));
        }
        if (uIConfigurations != null) {
            uIConfigurations.forEach(i -> i.setHotel(this));
        }
        this.uiConfigurations = uIConfigurations;
    }

    public Hotel uiConfigurations(Set<UIConfiguration> uIConfigurations) {
        this.setUiConfigurations(uIConfigurations);
        return this;
    }

    public Hotel addUiConfigurations(UIConfiguration uIConfiguration) {
        this.uiConfigurations.add(uIConfiguration);
        uIConfiguration.setHotel(this);
        return this;
    }

    public Hotel removeUiConfigurations(UIConfiguration uIConfiguration) {
        this.uiConfigurations.remove(uIConfiguration);
        uIConfiguration.setHotel(null);
        return this;
    }

    public Set<EmailTemplateConfiguration> getEmailConfigs() {
        return this.emailConfigs;
    }

    public void setEmailConfigs(Set<EmailTemplateConfiguration> emailTemplateConfigurations) {
        if (this.emailConfigs != null) {
            this.emailConfigs.forEach(i -> i.setHotel(null));
        }
        if (emailTemplateConfigurations != null) {
            emailTemplateConfigurations.forEach(i -> i.setHotel(this));
        }
        this.emailConfigs = emailTemplateConfigurations;
    }

    public Hotel emailConfigs(Set<EmailTemplateConfiguration> emailTemplateConfigurations) {
        this.setEmailConfigs(emailTemplateConfigurations);
        return this;
    }

    public Hotel addEmailConfig(EmailTemplateConfiguration emailTemplateConfiguration) {
        this.emailConfigs.add(emailTemplateConfiguration);
        emailTemplateConfiguration.setHotel(this);
        return this;
    }

    public Hotel removeEmailConfig(EmailTemplateConfiguration emailTemplateConfiguration) {
        this.emailConfigs.remove(emailTemplateConfiguration);
        emailTemplateConfiguration.setHotel(null);
        return this;
    }

    public Set<AuthentificationConfiguration> getAuthConfigs() {
        return this.authConfigs;
    }

    public void setAuthConfigs(Set<AuthentificationConfiguration> authentificationConfigurations) {
        if (this.authConfigs != null) {
            this.authConfigs.forEach(i -> i.setHotel(null));
        }
        if (authentificationConfigurations != null) {
            authentificationConfigurations.forEach(i -> i.setHotel(this));
        }
        this.authConfigs = authentificationConfigurations;
    }

    public Hotel authConfigs(Set<AuthentificationConfiguration> authentificationConfigurations) {
        this.setAuthConfigs(authentificationConfigurations);
        return this;
    }

    public Hotel addAuthConfig(AuthentificationConfiguration authentificationConfiguration) {
        this.authConfigs.add(authentificationConfiguration);
        authentificationConfiguration.setHotel(this);
        return this;
    }

    public Hotel removeAuthConfig(AuthentificationConfiguration authentificationConfiguration) {
        this.authConfigs.remove(authentificationConfiguration);
        authentificationConfiguration.setHotel(null);
        return this;
    }

    public HotelAdministrateur getHotelAdministrateur() {
        return this.hotelAdministrateur;
    }

    public void setHotelAdministrateur(HotelAdministrateur hotelAdministrateur) {
        this.hotelAdministrateur = hotelAdministrateur;
    }

    public Hotel hotelAdministrateur(HotelAdministrateur hotelAdministrateur) {
        this.setHotelAdministrateur(hotelAdministrateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hotel)) {
            return false;
        }
        return getId() != null && getId().equals(((Hotel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hotel{" +
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
            "}";
    }
}
