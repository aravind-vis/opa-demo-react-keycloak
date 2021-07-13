package com.opademo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.opademo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectUserRoleMapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectUserRoleMap.class);
        ProjectUserRoleMap projectUserRoleMap1 = new ProjectUserRoleMap();
        projectUserRoleMap1.setId(1L);
        ProjectUserRoleMap projectUserRoleMap2 = new ProjectUserRoleMap();
        projectUserRoleMap2.setId(projectUserRoleMap1.getId());
        assertThat(projectUserRoleMap1).isEqualTo(projectUserRoleMap2);
        projectUserRoleMap2.setId(2L);
        assertThat(projectUserRoleMap1).isNotEqualTo(projectUserRoleMap2);
        projectUserRoleMap1.setId(null);
        assertThat(projectUserRoleMap1).isNotEqualTo(projectUserRoleMap2);
    }
}
