package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthentificationConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthentificationConfigurationDTO.class);
        AuthentificationConfigurationDTO authentificationConfigurationDTO1 = new AuthentificationConfigurationDTO();
        authentificationConfigurationDTO1.setId(1L);
        AuthentificationConfigurationDTO authentificationConfigurationDTO2 = new AuthentificationConfigurationDTO();
        assertThat(authentificationConfigurationDTO1).isNotEqualTo(authentificationConfigurationDTO2);
        authentificationConfigurationDTO2.setId(authentificationConfigurationDTO1.getId());
        assertThat(authentificationConfigurationDTO1).isEqualTo(authentificationConfigurationDTO2);
        authentificationConfigurationDTO2.setId(2L);
        assertThat(authentificationConfigurationDTO1).isNotEqualTo(authentificationConfigurationDTO2);
        authentificationConfigurationDTO1.setId(null);
        assertThat(authentificationConfigurationDTO1).isNotEqualTo(authentificationConfigurationDTO2);
    }
}
