package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HotelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HotelDTO.class);
        HotelDTO hotelDTO1 = new HotelDTO();
        hotelDTO1.setId(1L);
        HotelDTO hotelDTO2 = new HotelDTO();
        assertThat(hotelDTO1).isNotEqualTo(hotelDTO2);
        hotelDTO2.setId(hotelDTO1.getId());
        assertThat(hotelDTO1).isEqualTo(hotelDTO2);
        hotelDTO2.setId(2L);
        assertThat(hotelDTO1).isNotEqualTo(hotelDTO2);
        hotelDTO1.setId(null);
        assertThat(hotelDTO1).isNotEqualTo(hotelDTO2);
    }
}
