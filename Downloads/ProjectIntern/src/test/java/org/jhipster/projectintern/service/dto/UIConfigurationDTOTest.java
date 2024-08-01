package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UIConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UIConfigurationDTO.class);
        UIConfigurationDTO uIConfigurationDTO1 = new UIConfigurationDTO();
        uIConfigurationDTO1.setId(1L);
        UIConfigurationDTO uIConfigurationDTO2 = new UIConfigurationDTO();
        assertThat(uIConfigurationDTO1).isNotEqualTo(uIConfigurationDTO2);
        uIConfigurationDTO2.setId(uIConfigurationDTO1.getId());
        assertThat(uIConfigurationDTO1).isEqualTo(uIConfigurationDTO2);
        uIConfigurationDTO2.setId(2L);
        assertThat(uIConfigurationDTO1).isNotEqualTo(uIConfigurationDTO2);
        uIConfigurationDTO1.setId(null);
        assertThat(uIConfigurationDTO1).isNotEqualTo(uIConfigurationDTO2);
    }
}
