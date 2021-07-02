package com.opademo.repository;

import com.opademo.domain.Project;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(
        value = "select distinct p from Project p left join ProjectUserRoleMap pum on pum.projectId=p.id where p.owner=:userId or pum.user=:userId"
    )
    Page<Project> findAllowedProjects(@Param("userId") String userId, Pageable pageable);
}
