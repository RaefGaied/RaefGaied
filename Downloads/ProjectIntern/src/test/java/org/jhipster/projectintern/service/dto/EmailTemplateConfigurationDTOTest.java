package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailTemplateConfigurationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTemplateConfigurationDTO.class);
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO1 = new EmailTemplateConfigurationDTO();
        emailTemplateConfigurationDTO1.setId(1L);
        EmailTemplateConfigurationDTO emailTemplateConfigurationDTO2 = new EmailTemplateConfigurationDTO();
        assertThat(emailTemplateConfigurationDTO1).isNotEqualTo(emailTemplateConfigurationDTO2);
        emailTemplateConfigurationDTO2.setId(emailTemplateConfigurationDTO1.getId());
        assertThat(emailTemplateConfigurationDTO1).isEqualTo(emailTemplateConfigurationDTO2);
        emailTemplateConfigurationDTO2.setId(2L);
        assertThat(emailTemplateConfigurationDTO1).isNotEqualTo(emailTemplateConfigurationDTO2);
        emailTemplateConfigurationDTO1.setId(null);
        assertThat(emailTemplateConfigurationDTO1).isNotEqualTo(emailTemplateConfigurationDTO2);
    }
}
