package org.jhipster.projectintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projectintern.domain.PaiementTestSamples.*;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaiementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiement.class);
        Paiement paiement1 = getPaiementSample1();
        Paiement paiement2 = new Paiement();
        assertThat(paiement1).isNotEqualTo(paiement2);

        paiement2.setId(paiement1.getId());
        assertThat(paiement1).isEqualTo(paiement2);

        paiement2 = getPaiementSample2();
        assertThat(paiement1).isNotEqualTo(paiement2);
    }
}
