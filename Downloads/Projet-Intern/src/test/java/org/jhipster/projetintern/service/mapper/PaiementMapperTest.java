package org.jhipster.projetintern.service.mapper;

import static org.jhipster.projetintern.domain.PaiementAsserts.*;
import static org.jhipster.projetintern.domain.PaiementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaiementMapperTest {

    private PaiementMapper paiementMapper;

    @BeforeEach
    void setUp() {
        paiementMapper = new PaiementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPaiementSample1();
        var actual = paiementMapper.toEntity(paiementMapper.toDto(expected));
        assertPaiementAllPropertiesEquals(expected, actual);
    }
}
