package org.jhipster.projectintern.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "montant")
    private Float montant;

    @Column(name = "date_paiement")
    private ZonedDateTime datePaiement;

    @Column(name = "methode_paiement")
    private String methodePaiement;

    @Column(name = "token")
    private String token;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Paiement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontant() {
        return this.montant;
    }

    public Paiement montant(Float montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public ZonedDateTime getDatePaiement() {
        return this.datePaiement;
    }

    public Paiement datePaiement(ZonedDateTime datePaiement) {
        this.setDatePaiement(datePaiement);
        return this;
    }

    public void setDatePaiement(ZonedDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getMethodePaiement() {
        return this.methodePaiement;
    }

    public Paiement methodePaiement(String methodePaiement) {
        this.setMethodePaiement(methodePaiement);
        return this;
    }

    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public String getToken() {
        return this.token;
    }

    public Paiement token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return this.description;
    }

    public Paiement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return getId() != null && getId().equals(((Paiement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", methodePaiement='" + getMethodePaiement() + "'" +
            ", token='" + getToken() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
