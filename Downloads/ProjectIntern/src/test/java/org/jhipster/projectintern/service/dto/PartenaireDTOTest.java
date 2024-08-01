package org.jhipster.projectintern.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.jhipster.projectintern.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartenaireDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartenaireDTO.class);
        PartenaireDTO partenaireDTO1 = new PartenaireDTO();
        partenaireDTO1.setId(1L);
        PartenaireDTO partenaireDTO2 = new PartenaireDTO();
        assertThat(partenaireDTO1).isNotEqualTo(partenaireDTO2);
        partenaireDTO2.setId(partenaireDTO1.getId());
        assertThat(partenaireDTO1).isEqualTo(partenaireDTO2);
        partenaireDTO2.setId(2L);
        assertThat(partenaireDTO1).isNotEqualTo(partenaireDTO2);
        partenaireDTO1.setId(null);
        assertThat(partenaireDTO1).isNotEqualTo(partenaireDTO2);
    }
}
