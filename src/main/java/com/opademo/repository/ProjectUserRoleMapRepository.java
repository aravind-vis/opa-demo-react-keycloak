package com.opademo.repository;

import com.opademo.domain.ProjectUserRoleMap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectUserRoleMap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectUserRoleMapRepository extends JpaRepository<ProjectUserRoleMap, Long> {
    List<ProjectUserRoleMap> findProjectUserRoleMapByUser(String user);
}
