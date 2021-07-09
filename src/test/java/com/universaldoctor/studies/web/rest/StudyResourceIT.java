package com.universaldoctor.studies.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universaldoctor.studies.IntegrationTest;
import com.universaldoctor.studies.domain.Study;
import com.universaldoctor.studies.repository.StudyRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link StudyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudyResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_PARTICIPANTS = 1;
    private static final Integer UPDATED_NUM_PARTICIPANTS = 2;

    private static final Integer DEFAULT_NUM_FORMS = 1;
    private static final Integer UPDATED_NUM_FORMS = 2;

    private static final String ENTITY_API_URL = "/api/studies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MockMvc restStudyMockMvc;

    private Study study;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Study createEntity() {
        Study study = new Study()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .numParticipants(DEFAULT_NUM_PARTICIPANTS)
            .numForms(DEFAULT_NUM_FORMS);
        return study;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Study createUpdatedEntity() {
        Study study = new Study()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .numParticipants(UPDATED_NUM_PARTICIPANTS)
            .numForms(UPDATED_NUM_FORMS);
        return study;
    }

    @BeforeEach
    public void initTest() {
        studyRepository.deleteAll();
        study = createEntity();
    }

    @Test
    void createStudy() throws Exception {
        int databaseSizeBeforeCreate = studyRepository.findAll().size();
        // Create the Study
        restStudyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isCreated());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeCreate + 1);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testStudy.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStudy.getNumParticipants()).isEqualTo(DEFAULT_NUM_PARTICIPANTS);
        assertThat(testStudy.getNumForms()).isEqualTo(DEFAULT_NUM_FORMS);
    }

    @Test
    void createStudyWithExistingId() throws Exception {
        // Create the Study with an existing ID
        study.setId("existing_id");

        int databaseSizeBeforeCreate = studyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllStudies() throws Exception {
        // Initialize the database
        studyRepository.save(study);

        // Get all the studyList
        restStudyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(study.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].numParticipants").value(hasItem(DEFAULT_NUM_PARTICIPANTS)))
            .andExpect(jsonPath("$.[*].numForms").value(hasItem(DEFAULT_NUM_FORMS)));
    }

    @Test
    void getStudy() throws Exception {
        // Initialize the database
        studyRepository.save(study);

        // Get the study
        restStudyMockMvc
            .perform(get(ENTITY_API_URL_ID, study.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(study.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.numParticipants").value(DEFAULT_NUM_PARTICIPANTS))
            .andExpect(jsonPath("$.numForms").value(DEFAULT_NUM_FORMS));
    }

    @Test
    void getNonExistingStudy() throws Exception {
        // Get the study
        restStudyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewStudy() throws Exception {
        // Initialize the database
        studyRepository.save(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study
        Study updatedStudy = studyRepository.findById(study.getId()).get();
        updatedStudy
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .numParticipants(UPDATED_NUM_PARTICIPANTS)
            .numForms(UPDATED_NUM_FORMS);

        restStudyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedStudy.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedStudy))
            )
            .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStudy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStudy.getNumParticipants()).isEqualTo(UPDATED_NUM_PARTICIPANTS);
        assertThat(testStudy.getNumForms()).isEqualTo(UPDATED_NUM_FORMS);
    }

    @Test
    void putNonExistingStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, study.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateStudyWithPatch() throws Exception {
        // Initialize the database
        studyRepository.save(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study using partial update
        Study partialUpdatedStudy = new Study();
        partialUpdatedStudy.setId(study.getId());

        partialUpdatedStudy.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).numParticipants(UPDATED_NUM_PARTICIPANTS);

        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudy))
            )
            .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStudy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStudy.getNumParticipants()).isEqualTo(UPDATED_NUM_PARTICIPANTS);
        assertThat(testStudy.getNumForms()).isEqualTo(DEFAULT_NUM_FORMS);
    }

    @Test
    void fullUpdateStudyWithPatch() throws Exception {
        // Initialize the database
        studyRepository.save(study);

        int databaseSizeBeforeUpdate = studyRepository.findAll().size();

        // Update the study using partial update
        Study partialUpdatedStudy = new Study();
        partialUpdatedStudy.setId(study.getId());

        partialUpdatedStudy
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .numParticipants(UPDATED_NUM_PARTICIPANTS)
            .numForms(UPDATED_NUM_FORMS);

        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudy))
            )
            .andExpect(status().isOk());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
        Study testStudy = studyList.get(studyList.size() - 1);
        assertThat(testStudy.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testStudy.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStudy.getNumParticipants()).isEqualTo(UPDATED_NUM_PARTICIPANTS);
        assertThat(testStudy.getNumForms()).isEqualTo(UPDATED_NUM_FORMS);
    }

    @Test
    void patchNonExistingStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, study.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(study))
            )
            .andExpect(status().isBadRequest());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamStudy() throws Exception {
        int databaseSizeBeforeUpdate = studyRepository.findAll().size();
        study.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudyMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(study)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Study in the database
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteStudy() throws Exception {
        // Initialize the database
        studyRepository.save(study);

        int databaseSizeBeforeDelete = studyRepository.findAll().size();

        // Delete the study
        restStudyMockMvc
            .perform(delete(ENTITY_API_URL_ID, study.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Study> studyList = studyRepository.findAll();
        assertThat(studyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
