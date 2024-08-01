package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AuthentificationConfigurationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AuthentificationConfiguration getAuthentificationConfigurationSample1() {
        return new AuthentificationConfiguration().id(1L).loginPageCustomization("loginPageCustomization1");
    }

    public static AuthentificationConfiguration getAuthentificationConfigurationSample2() {
        return new AuthentificationConfiguration().id(2L).loginPageCustomization("loginPageCustomization2");
    }

    public static AuthentificationConfiguration getAuthentificationConfigurationRandomSampleGenerator() {
        return new AuthentificationConfiguration().id(longCount.incrementAndGet()).loginPageCustomization(UUID.randomUUID().toString());
    }
}
