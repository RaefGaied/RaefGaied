package org.jhipster.projectintern.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link org.jhipster.projectintern.domain.Paiement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaiementDTO implements Serializable {

    private Long id;

    private Float montant;

    private ZonedDateTime datePaiement;

    private String methodePaiement;

    private String token;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public ZonedDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(ZonedDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementDTO)) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paiementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", methodePaiement='" + getMethodePaiement() + "'" +
            ", token='" + getToken() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
