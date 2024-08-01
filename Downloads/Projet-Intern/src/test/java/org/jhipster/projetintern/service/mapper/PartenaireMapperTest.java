package org.jhipster.projetintern.service.mapper;

import static org.jhipster.projetintern.domain.PartenaireAsserts.*;
import static org.jhipster.projetintern.domain.PartenaireTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartenaireMapperTest {

    private PartenaireMapper partenaireMapper;

    @BeforeEach
    void setUp() {
        partenaireMapper = new PartenaireMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartenaireSample1();
        var actual = partenaireMapper.toEntity(partenaireMapper.toDto(expected));
        assertPartenaireAllPropertiesEquals(expected, actual);
    }
}
