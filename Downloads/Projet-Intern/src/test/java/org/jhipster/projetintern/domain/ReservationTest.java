package org.jhipster.projetintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projetintern.domain.HotelTestSamples.*;
import static org.jhipster.projetintern.domain.ReservationTestSamples.*;
import static org.jhipster.projetintern.domain.ServiceTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.projetintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reservation.class);
        Reservation reservation1 = getReservationSample1();
        Reservation reservation2 = new Reservation();
        assertThat(reservation1).isNotEqualTo(reservation2);

        reservation2.setId(reservation1.getId());
        assertThat(reservation1).isEqualTo(reservation2);

        reservation2 = getReservationSample2();
        assertThat(reservation1).isNotEqualTo(reservation2);
    }

    @Test
    void serviceTest() {
        Reservation reservation = getReservationRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        reservation.addService(serviceBack);
        assertThat(reservation.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getReservation()).isEqualTo(reservation);

        reservation.removeService(serviceBack);
        assertThat(reservation.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getReservation()).isNull();

        reservation.services(new HashSet<>(Set.of(serviceBack)));
        assertThat(reservation.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getReservation()).isEqualTo(reservation);

        reservation.setServices(new HashSet<>());
        assertThat(reservation.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getReservation()).isNull();
    }

    @Test
    void hotelTest() {
        Reservation reservation = getReservationRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        reservation.setHotel(hotelBack);
        assertThat(reservation.getHotel()).isEqualTo(hotelBack);

        reservation.hotel(null);
        assertThat(reservation.getHotel()).isNull();
    }
}
