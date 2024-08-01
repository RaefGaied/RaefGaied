package org.jhipster.projectintern.service.mapper;

import static org.jhipster.projectintern.domain.UIConfigurationAsserts.*;
import static org.jhipster.projectintern.domain.UIConfigurationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UIConfigurationMapperTest {

    private UIConfigurationMapper uIConfigurationMapper;

    @BeforeEach
    void setUp() {
        uIConfigurationMapper = new UIConfigurationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUIConfigurationSample1();
        var actual = uIConfigurationMapper.toEntity(uIConfigurationMapper.toDto(expected));
        assertUIConfigurationAllPropertiesEquals(expected, actual);
    }
}
