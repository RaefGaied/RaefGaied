package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class HotelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Hotel getHotelSample1() {
        return new Hotel()
            .id(1L)
            .nom("nom1")
            .address("address1")
            .numTel("numTel1")
            .pays("pays1")
            .ville("ville1")
            .vueS("vueS1")
            .capacity(1)
            .notation("notation1")
            .lienUnique("lienUnique1");
    }

    public static Hotel getHotelSample2() {
        return new Hotel()
            .id(2L)
            .nom("nom2")
            .address("address2")
            .numTel("numTel2")
            .pays("pays2")
            .ville("ville2")
            .vueS("vueS2")
            .capacity(2)
            .notation("notation2")
            .lienUnique("lienUnique2");
    }

    public static Hotel getHotelRandomSampleGenerator() {
        return new Hotel()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .numTel(UUID.randomUUID().toString())
            .pays(UUID.randomUUID().toString())
            .ville(UUID.randomUUID().toString())
            .vueS(UUID.randomUUID().toString())
            .capacity(intCount.incrementAndGet())
            .notation(UUID.randomUUID().toString())
            .lienUnique(UUID.randomUUID().toString());
    }
}
