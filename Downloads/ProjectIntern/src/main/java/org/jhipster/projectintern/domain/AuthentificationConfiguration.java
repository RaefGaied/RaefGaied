package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuthentificationConfiguration.
 */
@Entity
@Table(name = "authentification_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthentificationConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "two_factor_enabled")
    private Boolean twoFactorEnabled;

    @Column(name = "login_page_customization")
    private String loginPageCustomization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "services", "uiConfigurations", "emailConfigs", "authConfigs", "hotelAdministrateur" },
        allowSetters = true
    )
    private Hotel hotel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuthentificationConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTwoFactorEnabled() {
        return this.twoFactorEnabled;
    }

    public AuthentificationConfiguration twoFactorEnabled(Boolean twoFactorEnabled) {
        this.setTwoFactorEnabled(twoFactorEnabled);
        return this;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getLoginPageCustomization() {
        return this.loginPageCustomization;
    }

    public AuthentificationConfiguration loginPageCustomization(String loginPageCustomization) {
        this.setLoginPageCustomization(loginPageCustomization);
        return this;
    }

    public void setLoginPageCustomization(String loginPageCustomization) {
        this.loginPageCustomization = loginPageCustomization;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public AuthentificationConfiguration hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthentificationConfiguration)) {
            return false;
        }
        return getId() != null && getId().equals(((AuthentificationConfiguration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthentificationConfiguration{" +
            "id=" + getId() +
            ", twoFactorEnabled='" + getTwoFactorEnabled() + "'" +
            ", loginPageCustomization='" + getLoginPageCustomization() + "'" +
            "}";
    }
}
