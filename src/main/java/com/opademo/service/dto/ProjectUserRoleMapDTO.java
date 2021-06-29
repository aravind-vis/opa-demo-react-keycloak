package com.opademo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.opademo.domain.ProjectUserRoleMap} entity.
 */
public class ProjectUserRoleMapDTO implements Serializable {

    private Long id;

    @NotNull
    private String user;

    private ProjectDTO projectId;

    private RoleDTO roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ProjectDTO getProjectId() {
        return projectId;
    }

    public void setProjectId(ProjectDTO projectId) {
        this.projectId = projectId;
    }

    public RoleDTO getRoleId() {
        return roleId;
    }

    public void setRoleId(RoleDTO roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectUserRoleMapDTO)) {
            return false;
        }

        ProjectUserRoleMapDTO projectUserRoleMapDTO = (ProjectUserRoleMapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectUserRoleMapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectUserRoleMapDTO{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", projectId=" + getProjectId() +
            ", roleId=" + getRoleId() +
            "}";
    }
}
