package com.opademo.service;

import com.opademo.service.dto.ProjectUserRoleMapDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.opademo.domain.ProjectUserRoleMap}.
 */
public interface ProjectUserRoleMapService {
    /**
     * Save a projectUserRoleMap.
     *
     * @param projectUserRoleMapDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectUserRoleMapDTO save(ProjectUserRoleMapDTO projectUserRoleMapDTO);

    /**
     * Partially updates a projectUserRoleMap.
     *
     * @param projectUserRoleMapDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectUserRoleMapDTO> partialUpdate(ProjectUserRoleMapDTO projectUserRoleMapDTO);

    /**
     * Get all the projectUserRoleMaps.
     *
     * @return the list of entities.
     */
    List<ProjectUserRoleMapDTO> findAll();

    /**
     * Get the "id" projectUserRoleMap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectUserRoleMapDTO> findOne(Long id);

    /**
     * Delete the "id" projectUserRoleMap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
