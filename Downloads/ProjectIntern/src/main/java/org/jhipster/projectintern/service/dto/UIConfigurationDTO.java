package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.UIConfiguration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UIConfigurationDTO implements Serializable {

    private Long id;

    private String colorSchema;

    private String logo;

    private String banner;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateModify;

    private HotelDTO hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColorSchema() {
        return colorSchema;
    }

    public void setColorSchema(String colorSchema) {
        this.colorSchema = colorSchema;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public ZonedDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateModify() {
        return dateModify;
    }

    public void setDateModify(ZonedDateTime dateModify) {
        this.dateModify = dateModify;
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
        if (!(o instanceof UIConfigurationDTO)) {
            return false;
        }

        UIConfigurationDTO uIConfigurationDTO = (UIConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, uIConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UIConfigurationDTO{" +
            "id=" + getId() +
            ", colorSchema='" + getColorSchema() + "'" +
            ", logo='" + getLogo() + "'" +
            ", banner='" + getBanner() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModify='" + getDateModify() + "'" +
            ", hotel=" + getHotel() +
            "}";
    }
}
