package com.opademo.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectUserRoleMapMapperTest {

    private ProjectUserRoleMapMapper projectUserRoleMapMapper;

    @BeforeEach
    public void setUp() {
        projectUserRoleMapMapper = new ProjectUserRoleMapMapperImpl();
    }
}
