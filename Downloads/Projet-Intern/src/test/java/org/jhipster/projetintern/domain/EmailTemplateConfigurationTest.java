package org.jhipster.projetintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projetintern.domain.EmailTemplateConfigurationTestSamples.*;
import static org.jhipster.projetintern.domain.HotelTestSamples.*;

import org.jhipster.projetintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailTemplateConfigurationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplateConfiguration.class);
        EmailTemplateConfiguration emailTemplateConfiguration1 = getEmailTemplateConfigurationSample1();
        EmailTemplateConfiguration emailTemplateConfiguration2 = new EmailTemplateConfiguration();
        assertThat(emailTemplateConfiguration1).isNotEqualTo(emailTemplateConfiguration2);

        emailTemplateConfiguration2.setId(emailTemplateConfiguration1.getId());
        assertThat(emailTemplateConfiguration1).isEqualTo(emailTemplateConfiguration2);

        emailTemplateConfiguration2 = getEmailTemplateConfigurationSample2();
        assertThat(emailTemplateConfiguration1).isNotEqualTo(emailTemplateConfiguration2);
    }

    @Test
    void hotelTest() {
        EmailTemplateConfiguration emailTemplateConfiguration = getEmailTemplateConfigurationRandomSampleGenerator();
        Hotel hotelBack = getHotelRandomSampleGenerator();

        emailTemplateConfiguration.setHotel(hotelBack);
        assertThat(emailTemplateConfiguration.getHotel()).isEqualTo(hotelBack);

        emailTemplateConfiguration.hotel(null);
        assertThat(emailTemplateConfiguration.getHotel()).isNull();
    }
}
