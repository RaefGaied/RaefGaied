package org.jhipster.projetintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projetintern.domain.HotelTestSamples.*;
import static org.jhipster.projetintern.domain.PartenaireTestSamples.*;
import static org.jhipster.projetintern.domain.ReservationTestSamples.*;
import static org.jhipster.projetintern.domain.ServiceTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.projetintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Service.class);
        Service service1 = getServiceSample1();
        Service service2 = new Service();
        assertThat(service1).isNotEqualTo(service2);

        service2.setId(service1.getId());
        assertThat(service1).isEqualTo(service2);

        service2 = getServiceSample2();
        assertThat(service1).isNotEqualTo(service2);
    }

    @Test
    void partenaireTest() {
        Service service = getServiceRandomSampleGenerator();
        Partenaire partenaireBack = getPartenaireRandomSampleGenerator();

        service.addPartenaire(partenaireBack);
        assertThat(service.getPartenaires()).containsOnly(partenaireBack);
        assertThat(partenaireBack.getService()).isEqualTo(service);

        service.removePartenaire(partenaireBack);
        assertThat(service.getPartenaires()).doesNotContain(partenaireBack);
        assertThat(partenaireBack.getService()).isNull();

        service.partenaires(new HashSet<>(Set.of(partenaireBack)));
        assertThat(service.getPartenaires()).containsOnly(partenaireBack);
        assertThat(partenaireBack.getService()).isEqualTo(service);

        service.setPartenaires(new HashSet<>());
        assertThat(service.getPartenaires()).doesNotContain(partenaireBack);
        assertThat(partenaireBack.getService()).isNull();
    }

    @Test
    void hotelTest() {
        Service service = getServiceRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        service.setHotel(hotelBack);
        assertThat(service.getHotel()).isEqualTo(hotelBack);

        service.hotel(null);
        assertThat(service.getHotel()).isNull();
    }

    @Test
    void partenaireTest() {
        Service service = getServiceRandomSampleGenerator();
        Partenaire partenaireBack = getPartenaireRandomSampleGenerator();

        service.setPartenaire(partenaireBack);
        assertThat(service.getPartenaire()).isEqualTo(partenaireBack);

        service.partenaire(null);
        assertThat(service.getPartenaire()).isNull();
    }

    @Test
    void reservationTest() {
        Service service = getServiceRandomSampleGenerator();
        Reservation reservationBack = getReservationRandomSampleGenerator();

        service.setReservation(reservationBack);
        assertThat(service.getReservation()).isEqualTo(reservationBack);

        service.reservation(null);
        assertThat(service.getReservation()).isNull();
    }
}
