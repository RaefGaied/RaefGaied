package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.AuthentificationConfiguration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuthentificationConfigurationDTO implements Serializable {

    private Long id;

    private Boolean twoFactorEnabled;

    private String loginPageCustomization;

    private HotelDTO hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public String getLoginPageCustomization() {
        return loginPageCustomization;
    }

    public void setLoginPageCustomization(String loginPageCustomization) {
        this.loginPageCustomization = loginPageCustomization;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthentificationConfigurationDTO)) {
            return false;
        }

        AuthentificationConfigurationDTO authentificationConfigurationDTO = (AuthentificationConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, authentificationConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthentificationConfigurationDTO{" +
            "id=" + getId() +
            ", twoFactorEnabled='" + getTwoFactorEnabled() + "'" +
            ", loginPageCustomization='" + getLoginPageCustomization() + "'" +
            ", hotel=" + getHotel() +
            "}";
    }
}
