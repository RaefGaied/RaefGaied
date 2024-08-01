package org.jhipster.projectintern.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jhipster.projectintern.domain.enumeration.Act;

/**
 * A EmailTemplateConfiguration.
 */
@Entity
@Table(name = "email_template_configuration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailTemplateConfiguration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_template")
    private String nomTemplate;

    @Column(name = "corps")
    private String corps;

    @Column(name = "date_creation")
    private ZonedDateTime dateCreation;

    @Column(name = "date_modify")
    private ZonedDateTime dateModify;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_status")
    private Act activeStatus;

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

    public EmailTemplateConfiguration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTemplate() {
        return this.nomTemplate;
    }

    public EmailTemplateConfiguration nomTemplate(String nomTemplate) {
        this.setNomTemplate(nomTemplate);
        return this;
    }

    public void setNomTemplate(String nomTemplate) {
        this.nomTemplate = nomTemplate;
    }

    public String getCorps() {
        return this.corps;
    }

    public EmailTemplateConfiguration corps(String corps) {
        this.setCorps(corps);
        return this;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public ZonedDateTime getDateCreation() {
        return this.dateCreation;
    }

    public EmailTemplateConfiguration dateCreation(ZonedDateTime dateCreation) {
        this.setDateCreation(dateCreation);
        return this;
    }

    public void setDateCreation(ZonedDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public ZonedDateTime getDateModify() {
        return this.dateModify;
    }

    public EmailTemplateConfiguration dateModify(ZonedDateTime dateModify) {
        this.setDateModify(dateModify);
        return this;
    }

    public void setDateModify(ZonedDateTime dateModify) {
        this.dateModify = dateModify;
    }

    public Act getActiveStatus() {
        return this.activeStatus;
    }

    public EmailTemplateConfiguration activeStatus(Act activeStatus) {
        this.setActiveStatus(activeStatus);
        return this;
    }

    public void setActiveStatus(Act activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Hotel getHotel() {
        return this.hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public EmailTemplateConfiguration hotel(Hotel hotel) {
        this.setHotel(hotel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTemplateConfiguration)) {
            return false;
        }
        return getId() != null && getId().equals(((EmailTemplateConfiguration) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailTemplateConfiguration{" +
            "id=" + getId() +
            ", nomTemplate='" + getNomTemplate() + "'" +
            ", corps='" + getCorps() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", dateModify='" + getDateModify() + "'" +
            ", activeStatus='" + getActiveStatus() + "'" +
            "}";
    }
}
