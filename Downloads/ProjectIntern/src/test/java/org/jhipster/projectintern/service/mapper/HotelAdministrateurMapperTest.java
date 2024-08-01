package org.jhipster.projectintern.service.mapper;

import static org.jhipster.projectintern.domain.HotelAdministrateurAsserts.*;
import static org.jhipster.projectintern.domain.HotelAdministrateurTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelAdministrateurMapperTest {

    private HotelAdministrateurMapper hotelAdministrateurMapper;

    @BeforeEach
    void setUp() {
        hotelAdministrateurMapper = new HotelAdministrateurMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHotelAdministrateurSample1();
        var actual = hotelAdministrateurMapper.toEntity(hotelAdministrateurMapper.toDto(expected));
        assertHotelAdministrateurAllPropertiesEquals(expected, actual);
    }
}
