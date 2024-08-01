package org.jhipster.projectintern.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.projectintern.domain.AuthentificationConfigurationTestSamples.*;
import static org.jhipster.projectintern.domain.EmailTemplateConfigurationTestSamples.*;
import static org.jhipster.projectintern.domain.HotelAdministrateurTestSamples.*;
import static org.jhipster.projectintern.domain.HotelTestSamples.*;
import static org.jhipster.projectintern.domain.ServiceTestSamples.*;
import static org.jhipster.projectintern.domain.UIConfigurationTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hotel.class);
        Hotel hotel1 = getHotelSample1();
        Hotel hotel2 = new Hotel();
        assertThat(hotel1).isNotEqualTo(hotel2);

        hotel2.setId(hotel1.getId());
        assertThat(hotel1).isEqualTo(hotel2);

        hotel2 = getHotelSample2();
        assertThat(hotel1).isNotEqualTo(hotel2);
    }

    @Test
    void servicesTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        Service serviceBack = getServiceRandomSampleGenerator();

        hotel.addServices(serviceBack);
        assertThat(hotel.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getHotel()).isEqualTo(hotel);

        hotel.removeServices(serviceBack);
        assertThat(hotel.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getHotel()).isNull();

        hotel.services(new HashSet<>(Set.of(serviceBack)));
        assertThat(hotel.getServices()).containsOnly(serviceBack);
        assertThat(serviceBack.getHotel()).isEqualTo(hotel);

        hotel.setServices(new HashSet<>());
        assertThat(hotel.getServices()).doesNotContain(serviceBack);
        assertThat(serviceBack.getHotel()).isNull();
    }

    @Test
    void uiConfigurationsTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        UIConfiguration uIConfigurationBack = getUIConfigurationRandomSampleGenerator();

        hotel.addUiConfigurations(uIConfigurationBack);
        assertThat(hotel.getUiConfigurations()).containsOnly(uIConfigurationBack);
        assertThat(uIConfigurationBack.getHotel()).isEqualTo(hotel);

        hotel.removeUiConfigurations(uIConfigurationBack);
        assertThat(hotel.getUiConfigurations()).doesNotContain(uIConfigurationBack);
        assertThat(uIConfigurationBack.getHotel()).isNull();

        hotel.uiConfigurations(new HashSet<>(Set.of(uIConfigurationBack)));
        assertThat(hotel.getUiConfigurations()).containsOnly(uIConfigurationBack);
        assertThat(uIConfigurationBack.getHotel()).isEqualTo(hotel);

        hotel.setUiConfigurations(new HashSet<>());
        assertThat(hotel.getUiConfigurations()).doesNotContain(uIConfigurationBack);
        assertThat(uIConfigurationBack.getHotel()).isNull();
    }

    @Test
    void emailConfigTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        EmailTemplateConfiguration emailTemplateConfigurationBack = getEmailTemplateConfigurationRandomSampleGenerator();

        hotel.addEmailConfig(emailTemplateConfigurationBack);
        assertThat(hotel.getEmailConfigs()).containsOnly(emailTemplateConfigurationBack);
        assertThat(emailTemplateConfigurationBack.getHotel()).isEqualTo(hotel);

        hotel.removeEmailConfig(emailTemplateConfigurationBack);
        assertThat(hotel.getEmailConfigs()).doesNotContain(emailTemplateConfigurationBack);
        assertThat(emailTemplateConfigurationBack.getHotel()).isNull();

        hotel.emailConfigs(new HashSet<>(Set.of(emailTemplateConfigurationBack)));
        assertThat(hotel.getEmailConfigs()).containsOnly(emailTemplateConfigurationBack);
        assertThat(emailTemplateConfigurationBack.getHotel()).isEqualTo(hotel);

        hotel.setEmailConfigs(new HashSet<>());
        assertThat(hotel.getEmailConfigs()).doesNotContain(emailTemplateConfigurationBack);
        assertThat(emailTemplateConfigurationBack.getHotel()).isNull();
    }

    @Test
    void authConfigTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        AuthentificationConfiguration authentificationConfigurationBack = getAuthentificationConfigurationRandomSampleGenerator();

        hotel.addAuthConfig(authentificationConfigurationBack);
        assertThat(hotel.getAuthConfigs()).containsOnly(authentificationConfigurationBack);
        assertThat(authentificationConfigurationBack.getHotel()).isEqualTo(hotel);

        hotel.removeAuthConfig(authentificationConfigurationBack);
        assertThat(hotel.getAuthConfigs()).doesNotContain(authentificationConfigurationBack);
        assertThat(authentificationConfigurationBack.getHotel()).isNull();

        hotel.authConfigs(new HashSet<>(Set.of(authentificationConfigurationBack)));
        assertThat(hotel.getAuthConfigs()).containsOnly(authentificationConfigurationBack);
        assertThat(authentificationConfigurationBack.getHotel()).isEqualTo(hotel);

        hotel.setAuthConfigs(new HashSet<>());
        assertThat(hotel.getAuthConfigs()).doesNotContain(authentificationConfigurationBack);
        assertThat(authentificationConfigurationBack.getHotel()).isNull();
    }

    @Test
    void hotelAdministrateurTest() {
        Hotel hotel = getHotelRandomSampleGenerator();
        HotelAdministrateur hotelAdministrateurBack = getHotelAdministrateurRandomSampleGenerator();

        hotel.setHotelAdministrateur(hotelAdministrateurBack);
        assertThat(hotel.getHotelAdministrateur()).isEqualTo(hotelAdministrateurBack);

        hotel.hotelAdministrateur(null);
        assertThat(hotel.getHotelAdministrateur()).isNull();
    }
}
