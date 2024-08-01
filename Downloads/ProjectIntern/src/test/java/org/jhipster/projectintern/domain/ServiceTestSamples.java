package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Service getServiceSample1() {
        return new Service()
            .id(1L)
            .nom("nom1")
            .description("description1")
            .disposability("disposability1")
            .capacite(1)
            .typeService("typeService1");
    }

    public static Service getServiceSample2() {
        return new Service()
            .id(2L)
            .nom("nom2")
            .description("description2")
            .disposability("disposability2")
            .capacite(2)
            .typeService("typeService2");
    }

    public static Service getServiceRandomSampleGenerator() {
        return new Service()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .disposability(UUID.randomUUID().toString())
            .capacite(intCount.incrementAndGet())
            .typeService(UUID.randomUUID().toString());
    }
}
