package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PaiementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Paiement getPaiementSample1() {
        return new Paiement().id(1L).methodePaiement("methodePaiement1").token("token1").description("description1");
    }

    public static Paiement getPaiementSample2() {
        return new Paiement().id(2L).methodePaiement("methodePaiement2").token("token2").description("description2");
    }

    public static Paiement getPaiementRandomSampleGenerator() {
        return new Paiement()
            .id(longCount.incrementAndGet())
            .methodePaiement(UUID.randomUUID().toString())
            .token(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
