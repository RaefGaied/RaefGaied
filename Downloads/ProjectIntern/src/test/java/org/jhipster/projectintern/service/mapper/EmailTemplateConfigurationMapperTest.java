package org.jhipster.projectintern.service.mapper;

import static org.jhipster.projectintern.domain.EmailTemplateConfigurationAsserts.*;
import static org.jhipster.projectintern.domain.EmailTemplateConfigurationTestSamples.*;

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
