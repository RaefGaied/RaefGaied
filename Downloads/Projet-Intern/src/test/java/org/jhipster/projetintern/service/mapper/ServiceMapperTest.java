package org.jhipster.projetintern.service.mapper;

import static org.jhipster.projetintern.domain.ServiceAsserts.*;
import static org.jhipster.projetintern.domain.ServiceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceMapperTest {

    private ServiceMapper serviceMapper;

    @BeforeEach
    void setUp() {
        serviceMapper = new ServiceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getServiceSample1();
        var actual = serviceMapper.toEntity(serviceMapper.toDto(expected));
        assertServiceAllPropertiesEquals(expected, actual);
    }
}
