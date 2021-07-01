package com.opademo.service.impl;

import com.opademo.domain.Project;
import com.opademo.domain.ProjectUserRoleMap;
import com.opademo.repository.ProjectRepository;
import com.opademo.repository.ProjectUserRoleMapRepository;
import com.opademo.repository.UserRepository;
import com.opademo.security.SecurityUtils;
import com.opademo.security.opa.OPAClient;
import com.opademo.service.ProjectService;
import com.opademo.service.ProjectUserRoleMapService;
import com.opademo.service.dto.ProjectDTO;
import com.opademo.service.dto.ProjectUserRoleMapDTO;
import com.opademo.service.mapper.ProjectUserRoleMapMapper;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectUserRoleMap}.
 */
@Service
@Transactional
public class ProjectUserRoleMapServiceImpl implements ProjectUserRoleMapService {

    private final Logger log = LoggerFactory.getLogger(ProjectUserRoleMapServiceImpl.class);

    private final ProjectUserRoleMapRepository projectUserRoleMapRepository;

    private final ProjectUserRoleMapMapper projectUserRoleMapMapper;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final OPAClient opaClient;

    public ProjectUserRoleMapServiceImpl(
        ProjectUserRoleMapRepository projectUserRoleMapRepository,
        ProjectUserRoleMapMapper projectUserRoleMapMapper,
        ProjectRepository projectRepository,
        OPAClient opaClient,
        UserRepository userRepository
    ) {
        this.projectUserRoleMapRepository = projectUserRoleMapRepository;
        this.projectUserRoleMapMapper = projectUserRoleMapMapper;
        this.projectRepository = projectRepository;
        this.opaClient = opaClient;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectUserRoleMapDTO save(ProjectUserRoleMapDTO projectUserRoleMapDTO, boolean validate) {
        log.debug("Request to save ProjectUserRoleMap : {}", projectUserRoleMapDTO);
        if (validate) {
            var project = projectRepository.findById(projectUserRoleMapDTO.getProjectId().getId()).orElse(new Project());

            if (!opaClient.authRequest("SHARE_PROJECT", getAuthInputMap(project))) {
                throw new AccessDeniedException("Access Denied");
            }
        }
        var projectUserRoleMap = projectUserRoleMapMapper.toEntity(projectUserRoleMapDTO);
        projectUserRoleMap = projectUserRoleMapRepository.save(projectUserRoleMap);
        return projectUserRoleMapMapper.toDto(projectUserRoleMap);
    }

    @Override
    public HashMap<String, Object> getAuthInputMap(Project project) {
        var user = SecurityUtils.getCurrentUserLogin().orElse("NA");
        var inputMap = new HashMap<String, Object>();
        inputMap.put("owner", project.getOwner());
        if (!project.getOwner().equals(user)) {
            var projectRole = projectUserRoleMapRepository.findProjectUserRoleMapByUser(user);
            var roleList = projectRole.stream().map(p -> p.getRoleId().getRoleName()).collect(Collectors.toList());

            var userFromDB = userRepository.findOneByLogin(user);
            inputMap.put("projectRole", roleList);
            inputMap.put("userRole", userFromDB.get().getAuthorities());
        }
        return inputMap;
    }

    @Override
    public Optional<ProjectUserRoleMapDTO> partialUpdate(ProjectUserRoleMapDTO projectUserRoleMapDTO) {
        log.debug("Request to partially update ProjectUserRoleMap : {}", projectUserRoleMapDTO);

        var project = projectRepository.findById(projectUserRoleMapDTO.getProjectId().getId()).orElse(new Project());

        if (!opaClient.authRequest("UPDATE_PROJECT", getAuthInputMap(project))) {
            throw new AccessDeniedException("Access Denied");
        }

        return projectUserRoleMapRepository
            .findById(projectUserRoleMapDTO.getId())
            .map(
                existingProjectUserRoleMap -> {
                    projectUserRoleMapMapper.partialUpdate(existingProjectUserRoleMap, projectUserRoleMapDTO);

                    return existingProjectUserRoleMap;
                }
            )
            .map(projectUserRoleMapRepository::save)
            .map(projectUserRoleMapMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectUserRoleMapDTO> findAll() {
        log.debug("Request to get all ProjectUserRoleMaps");
        return projectUserRoleMapRepository
            .findAll()
            .stream()
            .map(projectUserRoleMapMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectUserRoleMapDTO> findOne(Long id) {
        log.debug("Request to get ProjectUserRoleMap : {}", id);
        return projectUserRoleMapRepository.findById(id).map(projectUserRoleMapMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectUserRoleMap : {}", id);
        projectUserRoleMapRepository.deleteById(id);
    }
}
