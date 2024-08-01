package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelAdministrateurDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelAdministrateurDTO.class);
        HotelAdministrateurDTO hotelAdministrateurDTO1 = new HotelAdministrateurDTO();
        hotelAdministrateurDTO1.setId(1L);
        HotelAdministrateurDTO hotelAdministrateurDTO2 = new HotelAdministrateurDTO();
        assertThat(hotelAdministrateurDTO1).isNotEqualTo(hotelAdministrateurDTO2);
        hotelAdministrateurDTO2.setId(hotelAdministrateurDTO1.getId());
        assertThat(hotelAdministrateurDTO1).isEqualTo(hotelAdministrateurDTO2);
        hotelAdministrateurDTO2.setId(2L);
        assertThat(hotelAdministrateurDTO1).isNotEqualTo(hotelAdministrateurDTO2);
        hotelAdministrateurDTO1.setId(null);
        assertThat(hotelAdministrateurDTO1).isNotEqualTo(hotelAdministrateurDTO2);
    }
}
