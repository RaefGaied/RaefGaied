package org.jhipster.projectintern.service.mapper;

import static org.jhipster.projectintern.domain.AuthentificationConfigurationAsserts.*;
import static org.jhipster.projectintern.domain.AuthentificationConfigurationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthentificationConfigurationMapperTest {

    private AuthentificationConfigurationMapper authentificationConfigurationMapper;

    @BeforeEach
    void setUp() {
        authentificationConfigurationMapper = new AuthentificationConfigurationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAuthentificationConfigurationSample1();
        var actual = authentificationConfigurationMapper.toEntity(authentificationConfigurationMapper.toDto(expected));
        assertAuthentificationConfigurationAllPropertiesEquals(expected, actual);
    }
}
