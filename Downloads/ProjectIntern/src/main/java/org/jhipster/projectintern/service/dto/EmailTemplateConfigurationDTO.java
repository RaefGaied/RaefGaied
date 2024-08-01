package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.jhipster.projectintern.domain.enumeration.Act;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.EmailTemplateConfiguration} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailTemplateConfigurationDTO implements Serializable {

    private Long id;

    private String nomTemplate;

    private String corps;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateModify;

    private Act activeStatus;

    private HotelDTO hotel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTemplate() {
        return nomTemplate;
    }

    public void setNomTemplate(String nomTemplate) {
        this.nomTemplate = nomTemplate;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
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

    public Act getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Act activeStatus) {
        this.activeStatus = activeStatus;
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
        if (!(o instanceof EmailTemplateConfigurationDTO)) {
            return false;
        }

        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO = (EmailTemplateConfigurationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailTemplateConfigurationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailTemplateConfigurationDTO{" +
            "id=" + getId() +
            ", nomTemplate='" + getNomTemplate() + "'" +
            ", corps='" + getCorps() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModify='" + getDateModify() + "'" +
            ", activeStatus='" + getActiveStatus() + "'" +
            ", hotel=" + getHotel() +
            "}";
    }
}
