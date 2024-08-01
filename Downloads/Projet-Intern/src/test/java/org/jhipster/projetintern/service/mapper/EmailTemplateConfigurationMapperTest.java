package org.jhipster.projetintern.service.mapper;

import static org.jhipster.projetintern.domain.EmailTemplateConfigurationAsserts.*;
import static org.jhipster.projetintern.domain.EmailTemplateConfigurationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailTemplateConfigurationMapperTest {

    private EmailTemplateConfigurationMapper emailTemplateConfigurationMapper;

    @BeforeEach
    void setUp() {
        emailTemplateConfigurationMapper = new EmailTemplateConfigurationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEmailTemplateConfigurationSample1();
        var actual = emailTemplateConfigurationMapper.toEntity(emailTemplateConfigurationMapper.toDto(expected));
        assertEmailTemplateConfigurationAllPropertiesEquals(expected, actual);
    }
}
