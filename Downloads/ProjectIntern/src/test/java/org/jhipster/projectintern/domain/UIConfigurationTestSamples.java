package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UIConfigurationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UIConfiguration getUIConfigurationSample1() {
        return new UIConfiguration().id(1L).colorSchema("colorSchema1").logo("logo1").banner("banner1");
    }

    public static UIConfiguration getUIConfigurationSample2() {
        return new UIConfiguration().id(2L).colorSchema("colorSchema2").logo("logo2").banner("banner2");
    }

    public static UIConfiguration getUIConfigurationRandomSampleGenerator() {
        return new UIConfiguration()
            .id(longCount.incrementAndGet())
            .colorSchema(UUID.randomUUID().toString())
            .logo(UUID.randomUUID().toString())
            .banner(UUID.randomUUID().toString());
    }
}
