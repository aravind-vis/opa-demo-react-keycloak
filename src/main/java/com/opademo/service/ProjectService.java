package com.opademo.service;

import com.opademo.service.dto.ProjectDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.opademo.domain.Project}.
 */
public interface ProjectService {
    /**
     * Save a project.
     *
     * @param projectDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectDTO save(ProjectDTO projectDTO);

    /**
     * Partially updates a project.
     *
     * @param projectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectDTO> partialUpdate(ProjectDTO projectDTO);

    /**
     * Get all the projects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectDTO> findAll(Pageable pageable);

    /**
     * Get the "id" project.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectDTO> findOne(Long id);

    /**
     * Delete the "id" project.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
