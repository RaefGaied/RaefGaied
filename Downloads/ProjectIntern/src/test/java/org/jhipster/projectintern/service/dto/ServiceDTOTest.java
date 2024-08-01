package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceDTO.class);
        ServiceDTO serviceDTO1 = new ServiceDTO();
        serviceDTO1.setId(1L);
        ServiceDTO serviceDTO2 = new ServiceDTO();
        assertThat(serviceDTO1).isNotEqualTo(serviceDTO2);
        serviceDTO2.setId(serviceDTO1.getId());
        assertThat(serviceDTO1).isEqualTo(serviceDTO2);
        serviceDTO2.setId(2L);
        assertThat(serviceDTO1).isNotEqualTo(serviceDTO2);
        serviceDTO1.setId(null);
        assertThat(serviceDTO1).isNotEqualTo(serviceDTO2);
    }
}
