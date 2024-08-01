package org.jhipster.projectintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projectintern.domain.HotelAdministrateurTestSamples.*;
import static org.jhipster.projectintern.domain.HotelTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelAdministrateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelAdministrateur.class);
        HotelAdministrateur hotelAdministrateur1 = getHotelAdministrateurSample1();
        HotelAdministrateur hotelAdministrateur2 = new HotelAdministrateur();
        assertThat(hotelAdministrateur1).isNotEqualTo(hotelAdministrateur2);

        hotelAdministrateur2.setId(hotelAdministrateur1.getId());
        assertThat(hotelAdministrateur1).isEqualTo(hotelAdministrateur2);

        hotelAdministrateur2 = getHotelAdministrateurSample2();
        assertThat(hotelAdministrateur1).isNotEqualTo(hotelAdministrateur2);
    }

    @Test
    void hotelsTest() {
        HotelAdministrateur hotelAdministrateur = getHotelAdministrateurRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        hotelAdministrateur.addHotels(hotelBack);
        assertThat(hotelAdministrateur.getHotels()).containsOnly(hotelBack);
        assertThat(hotelBack.getHotelAdministrateur()).isEqualTo(hotelAdministrateur);

        hotelAdministrateur.removeHotels(hotelBack);
        assertThat(hotelAdministrateur.getHotels()).doesNotContain(hotelBack);
        assertThat(hotelBack.getHotelAdministrateur()).isNull();

        hotelAdministrateur.hotels(new HashSet<>(Set.of(hotelBack)));
        assertThat(hotelAdministrateur.getHotels()).containsOnly(hotelBack);
        assertThat(hotelBack.getHotelAdministrateur()).isEqualTo(hotelAdministrateur);

        hotelAdministrateur.setHotels(new HashSet<>());
        assertThat(hotelAdministrateur.getHotels()).doesNotContain(hotelBack);
        assertThat(hotelBack.getHotelAdministrateur()).isNull();
    }
}
