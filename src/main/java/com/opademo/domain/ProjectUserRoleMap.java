package com.opademo.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ProjectUserRoleMap.
 */
@Entity
@Table(name = "project_user_role_map")
public class ProjectUserRoleMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_user", nullable = false)
    private String user;

    @OneToOne
    @JoinColumn(unique = true)
    private Project projectId;

    @OneToOne
    @JoinColumn(unique = true)
    private Role roleId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectUserRoleMap id(Long id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return this.user;
    }

    public ProjectUserRoleMap user(String user) {
        this.user = user;
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Project getProjectId() {
        return this.projectId;
    }

    public ProjectUserRoleMap projectId(Project project) {
        this.setProjectId(project);
        return this;
    }

    public void setProjectId(Project project) {
        this.projectId = project;
    }

    public Role getRoleId() {
        return this.roleId;
    }

    public ProjectUserRoleMap roleId(Role role) {
        this.setRoleId(role);
        return this;
    }

    public void setRoleId(Role role) {
        this.roleId = role;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectUserRoleMap)) {
            return false;
        }
        return id != null && id.equals(((ProjectUserRoleMap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectUserRoleMap{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            "}";
    }
}
