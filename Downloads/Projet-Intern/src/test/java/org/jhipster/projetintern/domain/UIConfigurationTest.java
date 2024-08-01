package org.jhipster.projetintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projetintern.domain.HotelTestSamples.*;
import static org.jhipster.projetintern.domain.UIConfigurationTestSamples.*;

import org.jhipster.projetintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UIConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UIConfiguration.class);
        UIConfiguration uIConfiguration1 = getUIConfigurationSample1();
        UIConfiguration uIConfiguration2 = new UIConfiguration();
        assertThat(uIConfiguration1).isNotEqualTo(uIConfiguration2);

        uIConfiguration2.setId(uIConfiguration1.getId());
        assertThat(uIConfiguration1).isEqualTo(uIConfiguration2);

        uIConfiguration2 = getUIConfigurationSample2();
        assertThat(uIConfiguration1).isNotEqualTo(uIConfiguration2);
    }

    @Test
    void hotelTest() {
        UIConfiguration uIConfiguration = getUIConfigurationRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        uIConfiguration.setHotel(hotelBack);
        assertThat(uIConfiguration.getHotel()).isEqualTo(hotelBack);

        uIConfiguration.hotel(null);
        assertThat(uIConfiguration.getHotel()).isNull();
    }
}
