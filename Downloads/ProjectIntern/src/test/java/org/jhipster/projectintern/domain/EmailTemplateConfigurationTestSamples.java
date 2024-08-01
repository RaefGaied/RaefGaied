package org.jhipster.projectintern.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmailTemplateConfigurationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EmailTemplateConfiguration getEmailTemplateConfigurationSample1() {
        return new EmailTemplateConfiguration().id(1L).nomTemplate("nomTemplate1").corps("corps1");
    }

    public static EmailTemplateConfiguration getEmailTemplateConfigurationSample2() {
        return new EmailTemplateConfiguration().id(2L).nomTemplate("nomTemplate2").corps("corps2");
    }

    public static EmailTemplateConfiguration getEmailTemplateConfigurationRandomSampleGenerator() {
        return new EmailTemplateConfiguration()
            .id(longCount.incrementAndGet())
            .nomTemplate(UUID.randomUUID().toString())
            .corps(UUID.randomUUID().toString());
    }
}
