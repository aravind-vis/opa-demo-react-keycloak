package com.opademo.web.rest;

import com.opademo.domain.ProjectUserRoleMap;
import com.opademo.repository.ProjectUserRoleMapRepository;
import com.opademo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.opademo.domain.ProjectUserRoleMap}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectUserRoleMapResource {

    private final Logger log = LoggerFactory.getLogger(ProjectUserRoleMapResource.class);

    private static final String ENTITY_NAME = "projectUserRoleMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectUserRoleMapRepository projectUserRoleMapRepository;

    public ProjectUserRoleMapResource(ProjectUserRoleMapRepository projectUserRoleMapRepository) {
        this.projectUserRoleMapRepository = projectUserRoleMapRepository;
    }

    /**
     * {@code POST  /project-user-role-maps} : Create a new projectUserRoleMap.
     *
     * @param projectUserRoleMap the projectUserRoleMap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectUserRoleMap, or with status {@code 400 (Bad Request)} if the projectUserRoleMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-user-role-maps")
    public ResponseEntity<ProjectUserRoleMap> createProjectUserRoleMap(@Valid @RequestBody ProjectUserRoleMap projectUserRoleMap)
        throws URISyntaxException {
        log.debug("REST request to save ProjectUserRoleMap : {}", projectUserRoleMap);
        if (projectUserRoleMap.getId() != null) {
            throw new BadRequestAlertException("A new projectUserRoleMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectUserRoleMap result = projectUserRoleMapRepository.save(projectUserRoleMap);
        return ResponseEntity
            .created(new URI("/api/project-user-role-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-user-role-maps/:id} : Updates an existing projectUserRoleMap.
     *
     * @param id the id of the projectUserRoleMap to save.
     * @param projectUserRoleMap the projectUserRoleMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectUserRoleMap,
     * or with status {@code 400 (Bad Request)} if the projectUserRoleMap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectUserRoleMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-user-role-maps/{id}")
    public ResponseEntity<ProjectUserRoleMap> updateProjectUserRoleMap(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectUserRoleMap projectUserRoleMap
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectUserRoleMap : {}, {}", id, projectUserRoleMap);
        if (projectUserRoleMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectUserRoleMap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectUserRoleMapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectUserRoleMap result = projectUserRoleMapRepository.save(projectUserRoleMap);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectUserRoleMap.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-user-role-maps/:id} : Partial updates given fields of an existing projectUserRoleMap, field will ignore if it is null
     *
     * @param id the id of the projectUserRoleMap to save.
     * @param projectUserRoleMap the projectUserRoleMap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectUserRoleMap,
     * or with status {@code 400 (Bad Request)} if the projectUserRoleMap is not valid,
     * or with status {@code 404 (Not Found)} if the projectUserRoleMap is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectUserRoleMap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-user-role-maps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectUserRoleMap> partialUpdateProjectUserRoleMap(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectUserRoleMap projectUserRoleMap
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectUserRoleMap partially : {}, {}", id, projectUserRoleMap);
        if (projectUserRoleMap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectUserRoleMap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectUserRoleMapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectUserRoleMap> result = projectUserRoleMapRepository
            .findById(projectUserRoleMap.getId())
            .map(
                existingProjectUserRoleMap -> {
                    if (projectUserRoleMap.getUser() != null) {
                        existingProjectUserRoleMap.setUser(projectUserRoleMap.getUser());
                    }

                    return existingProjectUserRoleMap;
                }
            )
            .map(projectUserRoleMapRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectUserRoleMap.getId().toString())
        );
    }

    /**
     * {@code GET  /project-user-role-maps} : get all the projectUserRoleMaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectUserRoleMaps in body.
     */
    @GetMapping("/project-user-role-maps")
    public List<ProjectUserRoleMap> getAllProjectUserRoleMaps() {
        log.debug("REST request to get all ProjectUserRoleMaps");
        return projectUserRoleMapRepository.findAll();
    }

    /**
     * {@code GET  /project-user-role-maps/:id} : get the "id" projectUserRoleMap.
     *
     * @param id the id of the projectUserRoleMap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectUserRoleMap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-user-role-maps/{id}")
    public ResponseEntity<ProjectUserRoleMap> getProjectUserRoleMap(@PathVariable Long id) {
        log.debug("REST request to get ProjectUserRoleMap : {}", id);
        Optional<ProjectUserRoleMap> projectUserRoleMap = projectUserRoleMapRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projectUserRoleMap);
    }

    /**
     * {@code DELETE  /project-user-role-maps/:id} : delete the "id" projectUserRoleMap.
     *
     * @param id the id of the projectUserRoleMap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-user-role-maps/{id}")
    public ResponseEntity<Void> deleteProjectUserRoleMap(@PathVariable Long id) {
        log.debug("REST request to delete ProjectUserRoleMap : {}", id);
        projectUserRoleMapRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
