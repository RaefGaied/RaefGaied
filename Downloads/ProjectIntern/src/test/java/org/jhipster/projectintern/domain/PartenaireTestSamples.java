package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PartenaireTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Partenaire getPartenaireSample1() {
        return new Partenaire()
            .id(1L)
            .description("description1")
            .nom("nom1")
            .contact("contact1")
            .adresse("adresse1")
            .typePartenaire("typePartenaire1");
    }

    public static Partenaire getPartenaireSample2() {
        return new Partenaire()
            .id(2L)
            .description("description2")
            .nom("nom2")
            .contact("contact2")
            .adresse("adresse2")
            .typePartenaire("typePartenaire2");
    }

    public static Partenaire getPartenaireRandomSampleGenerator() {
        return new Partenaire()
            .id(longCount.incrementAndGet())
            .description(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .contact(UUID.randomUUID().toString())
            .adresse(UUID.randomUUID().toString())
            .typePartenaire(UUID.randomUUID().toString());
    }
}
