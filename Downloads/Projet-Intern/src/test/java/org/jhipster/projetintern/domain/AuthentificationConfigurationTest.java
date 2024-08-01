package org.jhipster.projetintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projetintern.domain.AuthentificationConfigurationTestSamples.*;
import static org.jhipster.projetintern.domain.HotelTestSamples.*;

import org.jhipster.projetintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthentificationConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthentificationConfiguration.class);
        AuthentificationConfiguration authentificationConfiguration1 = getAuthentificationConfigurationSample1();
        AuthentificationConfiguration authentificationConfiguration2 = new AuthentificationConfiguration();
        assertThat(authentificationConfiguration1).isNotEqualTo(authentificationConfiguration2);

        authentificationConfiguration2.setId(authentificationConfiguration1.getId());
        assertThat(authentificationConfiguration1).isEqualTo(authentificationConfiguration2);

        authentificationConfiguration2 = getAuthentificationConfigurationSample2();
        assertThat(authentificationConfiguration1).isNotEqualTo(authentificationConfiguration2);
    }

    @Test
    void hotelTest() {
        AuthentificationConfiguration authentificationConfiguration = getAuthentificationConfigurationRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        authentificationConfiguration.setHotel(hotelBack);
        assertThat(authentificationConfiguration.getHotel()).isEqualTo(hotelBack);

        authentificationConfiguration.hotel(null);
        assertThat(authentificationConfiguration.getHotel()).isNull();
    }
}
