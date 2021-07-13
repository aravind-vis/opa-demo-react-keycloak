package com.opademo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.opademo.IntegrationTest;
import com.opademo.domain.ProjectUserRoleMap;
import com.opademo.repository.ProjectUserRoleMapRepository;
import com.opademo.service.dto.ProjectUserRoleMapDTO;
import com.opademo.service.mapper.ProjectUserRoleMapMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProjectUserRoleMapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectUserRoleMapResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-user-role-maps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectUserRoleMapRepository projectUserRoleMapRepository;

    @Autowired
    private ProjectUserRoleMapMapper projectUserRoleMapMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectUserRoleMapMockMvc;

    private ProjectUserRoleMap projectUserRoleMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectUserRoleMap createEntity(EntityManager em) {
        ProjectUserRoleMap projectUserRoleMap = new ProjectUserRoleMap().user(DEFAULT_USER);
        return projectUserRoleMap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectUserRoleMap createUpdatedEntity(EntityManager em) {
        ProjectUserRoleMap projectUserRoleMap = new ProjectUserRoleMap().user(UPDATED_USER);
        return projectUserRoleMap;
    }

    @BeforeEach
    public void initTest() {
        projectUserRoleMap = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeCreate = projectUserRoleMapRepository.findAll().size();
        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);
        restProjectUserRoleMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectUserRoleMap testProjectUserRoleMap = projectUserRoleMapList.get(projectUserRoleMapList.size() - 1);
        assertThat(testProjectUserRoleMap.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    void createProjectUserRoleMapWithExistingId() throws Exception {
        // Create the ProjectUserRoleMap with an existing ID
        projectUserRoleMap.setId(1L);
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        int databaseSizeBeforeCreate = projectUserRoleMapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectUserRoleMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectUserRoleMapRepository.findAll().size();
        // set the field null
        projectUserRoleMap.setUser(null);

        // Create the ProjectUserRoleMap, which fails.
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        restProjectUserRoleMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProjectUserRoleMaps() throws Exception {
        // Initialize the database
        projectUserRoleMapRepository.saveAndFlush(projectUserRoleMap);

        // Get all the projectUserRoleMapList
        restProjectUserRoleMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectUserRoleMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));
    }

    @Test
    @Transactional
    void getProjectUserRoleMap() throws Exception {
        // Initialize the database
        projectUserRoleMapRepository.saveAndFlush(projectUserRoleMap);

        // Get the projectUserRoleMap
        restProjectUserRoleMapMockMvc
            .perform(get(ENTITY_API_URL_ID, projectUserRoleMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectUserRoleMap.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER));
    }

    @Test
    @Transactional
    void getNonExistingProjectUserRoleMap() throws Exception {
        // Get the projectUserRoleMap
        restProjectUserRoleMapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectUserRoleMap() throws Exception {
        // Initialize the database
        projectUserRoleMapRepository.saveAndFlush(projectUserRoleMap);

        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();

        // Update the projectUserRoleMap
        ProjectUserRoleMap updatedProjectUserRoleMap = projectUserRoleMapRepository.findById(projectUserRoleMap.getId()).get();
        // Disconnect from session so that the updates on updatedProjectUserRoleMap are not directly saved in db
        em.detach(updatedProjectUserRoleMap);
        updatedProjectUserRoleMap.user(UPDATED_USER);
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(updatedProjectUserRoleMap);

        restProjectUserRoleMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectUserRoleMapDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
        ProjectUserRoleMap testProjectUserRoleMap = projectUserRoleMapList.get(projectUserRoleMapList.size() - 1);
        assertThat(testProjectUserRoleMap.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    void putNonExistingProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();
        projectUserRoleMap.setId(count.incrementAndGet());

        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectUserRoleMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectUserRoleMapDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();
        projectUserRoleMap.setId(count.incrementAndGet());

        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectUserRoleMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();
        projectUserRoleMap.setId(count.incrementAndGet());

        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectUserRoleMapMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectUserRoleMapWithPatch() throws Exception {
        // Initialize the database
        projectUserRoleMapRepository.saveAndFlush(projectUserRoleMap);

        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();

        // Update the projectUserRoleMap using partial update
        ProjectUserRoleMap partialUpdatedProjectUserRoleMap = new ProjectUserRoleMap();
        partialUpdatedProjectUserRoleMap.setId(projectUserRoleMap.getId());

        restProjectUserRoleMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectUserRoleMap.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectUserRoleMap))
            )
            .andExpect(status().isOk());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
        ProjectUserRoleMap testProjectUserRoleMap = projectUserRoleMapList.get(projectUserRoleMapList.size() - 1);
        assertThat(testProjectUserRoleMap.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    void fullUpdateProjectUserRoleMapWithPatch() throws Exception {
        // Initialize the database
        projectUserRoleMapRepository.saveAndFlush(projectUserRoleMap);

        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();

        // Update the projectUserRoleMap using partial update
        ProjectUserRoleMap partialUpdatedProjectUserRoleMap = new ProjectUserRoleMap();
        partialUpdatedProjectUserRoleMap.setId(projectUserRoleMap.getId());

        partialUpdatedProjectUserRoleMap.user(UPDATED_USER);

        restProjectUserRoleMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectUserRoleMap.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectUserRoleMap))
            )
            .andExpect(status().isOk());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
        ProjectUserRoleMap testProjectUserRoleMap = projectUserRoleMapList.get(projectUserRoleMapList.size() - 1);
        assertThat(testProjectUserRoleMap.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    void patchNonExistingProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();
        projectUserRoleMap.setId(count.incrementAndGet());

        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectUserRoleMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectUserRoleMapDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();
        projectUserRoleMap.setId(count.incrementAndGet());

        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectUserRoleMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectUserRoleMap() throws Exception {
        int databaseSizeBeforeUpdate = projectUserRoleMapRepository.findAll().size();
        projectUserRoleMap.setId(count.incrementAndGet());

        // Create the ProjectUserRoleMap
        ProjectUserRoleMapDTO projectUserRoleMapDTO = projectUserRoleMapMapper.toDto(projectUserRoleMap);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectUserRoleMapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectUserRoleMapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectUserRoleMap in the database
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectUserRoleMap() throws Exception {
        // Initialize the database
        projectUserRoleMapRepository.saveAndFlush(projectUserRoleMap);

        int databaseSizeBeforeDelete = projectUserRoleMapRepository.findAll().size();

        // Delete the projectUserRoleMap
        restProjectUserRoleMapMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectUserRoleMap.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectUserRoleMap> projectUserRoleMapList = projectUserRoleMapRepository.findAll();
        assertThat(projectUserRoleMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
