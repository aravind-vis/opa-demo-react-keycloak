package com.opademo.service.impl;

import com.opademo.domain.ProjectUserRoleMap;
import com.opademo.repository.ProjectUserRoleMapRepository;
import com.opademo.service.ProjectUserRoleMapService;
import com.opademo.service.dto.ProjectUserRoleMapDTO;
import com.opademo.service.mapper.ProjectUserRoleMapMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ProjectUserRoleMapServiceImpl(
        ProjectUserRoleMapRepository projectUserRoleMapRepository,
        ProjectUserRoleMapMapper projectUserRoleMapMapper
    ) {
        this.projectUserRoleMapRepository = projectUserRoleMapRepository;
        this.projectUserRoleMapMapper = projectUserRoleMapMapper;
    }

    @Override
    public ProjectUserRoleMapDTO save(ProjectUserRoleMapDTO projectUserRoleMapDTO) {
        log.debug("Request to save ProjectUserRoleMap : {}", projectUserRoleMapDTO);
        ProjectUserRoleMap projectUserRoleMap = projectUserRoleMapMapper.toEntity(projectUserRoleMapDTO);
        projectUserRoleMap = projectUserRoleMapRepository.save(projectUserRoleMap);
        return projectUserRoleMapMapper.toDto(projectUserRoleMap);
    }

    @Override
    public Optional<ProjectUserRoleMapDTO> partialUpdate(ProjectUserRoleMapDTO projectUserRoleMapDTO) {
        log.debug("Request to partially update ProjectUserRoleMap : {}", projectUserRoleMapDTO);

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
