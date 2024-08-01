package org.jhipster.projetintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projetintern.domain.PartenaireTestSamples.*;
import static org.jhipster.projetintern.domain.ServiceTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.projetintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartenaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partenaire.class);
        Partenaire partenaire1 = getPartenaireSample1();
        Partenaire partenaire2 = new Partenaire();
        assertThat(partenaire1).isNotEqualTo(partenaire2);

        partenaire2.setId(partenaire1.getId());
        assertThat(partenaire1).isEqualTo(partenaire2);

        partenaire2 = getPartenaireSample2();
        assertThat(partenaire1).isNotEqualTo(partenaire2);
    }

    @Test
    void servicesTest() {
        Partenaire partenaire = getPartenaireRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        partenaire.addServices(serviceBack);
        assertThat(partenaire.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getPartenaire()).isEqualTo(partenaire);

        partenaire.removeServices(serviceBack);
        assertThat(partenaire.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getPartenaire()).isNull();

        partenaire.services(new HashSet<>(Set.of(serviceBack)));
        assertThat(partenaire.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getPartenaire()).isEqualTo(partenaire);

        partenaire.setServices(new HashSet<>());
        assertThat(partenaire.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getPartenaire()).isNull();
    }

    @Test
    void serviceTest() {
        Partenaire partenaire = getPartenaireRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        partenaire.setService(serviceBack);
        assertThat(partenaire.getService()).isEqualTo(serviceBack);

        partenaire.service(null);
        assertThat(partenaire.getService()).isNull();
    }
}
