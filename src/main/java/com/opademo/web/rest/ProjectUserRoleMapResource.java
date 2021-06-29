package com.opademo.web.rest;

import com.opademo.repository.ProjectUserRoleMapRepository;
import com.opademo.service.ProjectUserRoleMapService;
import com.opademo.service.dto.ProjectUserRoleMapDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.opademo.domain.ProjectUserRoleMap}.
 */
@RestController
@RequestMapping("/api")
public class ProjectUserRoleMapResource {

    private final Logger log = LoggerFactory.getLogger(ProjectUserRoleMapResource.class);

    private static final String ENTITY_NAME = "projectUserRoleMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectUserRoleMapService projectUserRoleMapService;

    private final ProjectUserRoleMapRepository projectUserRoleMapRepository;

    public ProjectUserRoleMapResource(
        ProjectUserRoleMapService projectUserRoleMapService,
        ProjectUserRoleMapRepository projectUserRoleMapRepository
    ) {
        this.projectUserRoleMapService = projectUserRoleMapService;
        this.projectUserRoleMapRepository = projectUserRoleMapRepository;
    }

    /**
     * {@code POST  /project-user-role-maps} : Create a new projectUserRoleMap.
     *
     * @param projectUserRoleMapDTO the projectUserRoleMapDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectUserRoleMapDTO, or with status {@code 400 (Bad Request)} if the projectUserRoleMap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-user-role-maps")
    public ResponseEntity<ProjectUserRoleMapDTO> createProjectUserRoleMap(@Valid @RequestBody ProjectUserRoleMapDTO projectUserRoleMapDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectUserRoleMap : {}", projectUserRoleMapDTO);
        if (projectUserRoleMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectUserRoleMap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectUserRoleMapDTO result = projectUserRoleMapService.save(projectUserRoleMapDTO);
        return ResponseEntity
            .created(new URI("/api/project-user-role-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-user-role-maps/:id} : Updates an existing projectUserRoleMap.
     *
     * @param id the id of the projectUserRoleMapDTO to save.
     * @param projectUserRoleMapDTO the projectUserRoleMapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectUserRoleMapDTO,
     * or with status {@code 400 (Bad Request)} if the projectUserRoleMapDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectUserRoleMapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-user-role-maps/{id}")
    public ResponseEntity<ProjectUserRoleMapDTO> updateProjectUserRoleMap(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProjectUserRoleMapDTO projectUserRoleMapDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectUserRoleMap : {}, {}", id, projectUserRoleMapDTO);
        if (projectUserRoleMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectUserRoleMapDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectUserRoleMapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectUserRoleMapDTO result = projectUserRoleMapService.save(projectUserRoleMapDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectUserRoleMapDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-user-role-maps/:id} : Partial updates given fields of an existing projectUserRoleMap, field will ignore if it is null
     *
     * @param id the id of the projectUserRoleMapDTO to save.
     * @param projectUserRoleMapDTO the projectUserRoleMapDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectUserRoleMapDTO,
     * or with status {@code 400 (Bad Request)} if the projectUserRoleMapDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectUserRoleMapDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectUserRoleMapDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-user-role-maps/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectUserRoleMapDTO> partialUpdateProjectUserRoleMap(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProjectUserRoleMapDTO projectUserRoleMapDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectUserRoleMap partially : {}, {}", id, projectUserRoleMapDTO);
        if (projectUserRoleMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectUserRoleMapDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectUserRoleMapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectUserRoleMapDTO> result = projectUserRoleMapService.partialUpdate(projectUserRoleMapDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projectUserRoleMapDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-user-role-maps} : get all the projectUserRoleMaps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectUserRoleMaps in body.
     */
    @GetMapping("/project-user-role-maps")
    public List<ProjectUserRoleMapDTO> getAllProjectUserRoleMaps() {
        log.debug("REST request to get all ProjectUserRoleMaps");
        return projectUserRoleMapService.findAll();
    }

    /**
     * {@code GET  /project-user-role-maps/:id} : get the "id" projectUserRoleMap.
     *
     * @param id the id of the projectUserRoleMapDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectUserRoleMapDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-user-role-maps/{id}")
    public ResponseEntity<ProjectUserRoleMapDTO> getProjectUserRoleMap(@PathVariable Long id) {
        log.debug("REST request to get ProjectUserRoleMap : {}", id);
        Optional<ProjectUserRoleMapDTO> projectUserRoleMapDTO = projectUserRoleMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectUserRoleMapDTO);
    }

    /**
     * {@code DELETE  /project-user-role-maps/:id} : delete the "id" projectUserRoleMap.
     *
     * @param id the id of the projectUserRoleMapDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-user-role-maps/{id}")
    public ResponseEntity<Void> deleteProjectUserRoleMap(@PathVariable Long id) {
        log.debug("REST request to delete ProjectUserRoleMap : {}", id);
        projectUserRoleMapService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
