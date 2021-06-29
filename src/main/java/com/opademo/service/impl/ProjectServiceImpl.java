package com.opademo.service.impl;

import com.opademo.domain.Project;
import com.opademo.repository.ProjectRepository;
import com.opademo.security.SecurityUtils;
import com.opademo.service.ProjectService;
import com.opademo.service.ProjectUserRoleMapService;
import com.opademo.service.RoleService;
import com.opademo.service.dto.ProjectDTO;
import com.opademo.service.dto.ProjectUserRoleMapDTO;
import com.opademo.service.dto.RoleDTO;
import com.opademo.service.mapper.ProjectMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Project}.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    private final RoleService roleService;

    private final ProjectUserRoleMapService projectMapService;

    public ProjectServiceImpl(
        ProjectRepository projectRepository,
        ProjectMapper projectMapper,
        RoleService roleService,
        ProjectUserRoleMapService projectMapService
    ) {
        this.projectRepository = projectRepository;
        this.roleService = roleService;
        this.projectMapService = projectMapService;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        log.debug("Request to save Project : {}", projectDTO);
        Project project = projectMapper.toEntity(projectDTO);
        project.setOwner(SecurityUtils.getCurrentUserLogin().orElse("NA"));
        project = projectRepository.save(project);
        var projectUserRoleMapDTO = new ProjectUserRoleMapDTO();
        var role = roleService.findByRoleName("OWNER").orElseGet(RoleDTO::new);
        var result = projectMapper.toDto(project);
        projectUserRoleMapDTO.setProjectId(result);
        projectUserRoleMapDTO.setRoleId(role);
        projectUserRoleMapDTO.setUser(project.getOwner());
        projectMapService.save(projectUserRoleMapDTO);
        return result;
    }

    @Override
    public Optional<ProjectDTO> partialUpdate(ProjectDTO projectDTO) {
        log.debug("Request to partially update Project : {}", projectDTO);

        return projectRepository
            .findById(projectDTO.getId())
            .map(
                existingProject -> {
                    projectMapper.partialUpdate(existingProject, projectDTO);

                    return existingProject;
                }
            )
            .map(projectRepository::save)
            .map(projectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        return projectRepository.findAll(pageable).map(projectMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        return projectRepository.findById(id).map(projectMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.deleteById(id);
    }
}
