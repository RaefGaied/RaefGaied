package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HotelAdministrateurTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HotelAdministrateur getHotelAdministrateurSample1() {
        return new HotelAdministrateur().id(1L).nom("nom1").email("email1").motDePasse("motDePasse1");
    }

    public static HotelAdministrateur getHotelAdministrateurSample2() {
        return new HotelAdministrateur().id(2L).nom("nom2").email("email2").motDePasse("motDePasse2");
    }

    public static HotelAdministrateur getHotelAdministrateurRandomSampleGenerator() {
        return new HotelAdministrateur()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .motDePasse(UUID.randomUUID().toString());
    }
}
