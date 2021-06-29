package com.opademo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.opademo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectUserRoleMapDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectUserRoleMapDTO.class);
        ProjectUserRoleMapDTO projectUserRoleMapDTO1 = new ProjectUserRoleMapDTO();
        projectUserRoleMapDTO1.setId(1L);
        ProjectUserRoleMapDTO projectUserRoleMapDTO2 = new ProjectUserRoleMapDTO();
        assertThat(projectUserRoleMapDTO1).isNotEqualTo(projectUserRoleMapDTO2);
        projectUserRoleMapDTO2.setId(projectUserRoleMapDTO1.getId());
        assertThat(projectUserRoleMapDTO1).isEqualTo(projectUserRoleMapDTO2);
        projectUserRoleMapDTO2.setId(2L);
        assertThat(projectUserRoleMapDTO1).isNotEqualTo(projectUserRoleMapDTO2);
        projectUserRoleMapDTO1.setId(null);
        assertThat(projectUserRoleMapDTO1).isNotEqualTo(projectUserRoleMapDTO2);
    }
}
