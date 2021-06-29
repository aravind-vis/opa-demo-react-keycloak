package com.opademo.service.mapper;

import com.opademo.domain.*;
import com.opademo.service.dto.ProjectUserRoleMapDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectUserRoleMap} and its DTO {@link ProjectUserRoleMapDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProjectMapper.class, RoleMapper.class })
public interface ProjectUserRoleMapMapper extends EntityMapper<ProjectUserRoleMapDTO, ProjectUserRoleMap> {
    @Mapping(target = "projectId", source = "projectId", qualifiedByName = "id")
    @Mapping(target = "roleId", source = "roleId", qualifiedByName = "id")
    ProjectUserRoleMapDTO toDto(ProjectUserRoleMap s);
}
