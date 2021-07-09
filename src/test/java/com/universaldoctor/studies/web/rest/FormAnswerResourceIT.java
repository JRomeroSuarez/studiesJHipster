package com.universaldoctor.studies.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universaldoctor.studies.IntegrationTest;
import com.universaldoctor.studies.domain.FormAnswer;
import com.universaldoctor.studies.repository.FormAnswerRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link FormAnswerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormAnswerResourceIT {

    private static final Instant DEFAULT_CREATION_FORM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_FORM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED_FORM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_FORM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/form-answers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FormAnswerRepository formAnswerRepository;

    @Autowired
    private MockMvc restFormAnswerMockMvc;

    private FormAnswer formAnswer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormAnswer createEntity() {
        FormAnswer formAnswer = new FormAnswer().creationForm(DEFAULT_CREATION_FORM).modifiedForm(DEFAULT_MODIFIED_FORM);
        return formAnswer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FormAnswer createUpdatedEntity() {
        FormAnswer formAnswer = new FormAnswer().creationForm(UPDATED_CREATION_FORM).modifiedForm(UPDATED_MODIFIED_FORM);
        return formAnswer;
    }

    @BeforeEach
    public void initTest() {
        formAnswerRepository.deleteAll();
        formAnswer = createEntity();
    }

    @Test
    void createFormAnswer() throws Exception {
        int databaseSizeBeforeCreate = formAnswerRepository.findAll().size();
        // Create the FormAnswer
        restFormAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formAnswer)))
            .andExpect(status().isCreated());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        FormAnswer testFormAnswer = formAnswerList.get(formAnswerList.size() - 1);
        assertThat(testFormAnswer.getCreationForm()).isEqualTo(DEFAULT_CREATION_FORM);
        assertThat(testFormAnswer.getModifiedForm()).isEqualTo(DEFAULT_MODIFIED_FORM);
    }

    @Test
    void createFormAnswerWithExistingId() throws Exception {
        // Create the FormAnswer with an existing ID
        formAnswer.setId("existing_id");

        int databaseSizeBeforeCreate = formAnswerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormAnswerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFormAnswers() throws Exception {
        // Initialize the database
        formAnswerRepository.save(formAnswer);

        // Get all the formAnswerList
        restFormAnswerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formAnswer.getId())))
            .andExpect(jsonPath("$.[*].creationForm").value(hasItem(DEFAULT_CREATION_FORM.toString())))
            .andExpect(jsonPath("$.[*].modifiedForm").value(hasItem(DEFAULT_MODIFIED_FORM.toString())));
    }

    @Test
    void getFormAnswer() throws Exception {
        // Initialize the database
        formAnswerRepository.save(formAnswer);

        // Get the formAnswer
        restFormAnswerMockMvc
            .perform(get(ENTITY_API_URL_ID, formAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(formAnswer.getId()))
            .andExpect(jsonPath("$.creationForm").value(DEFAULT_CREATION_FORM.toString()))
            .andExpect(jsonPath("$.modifiedForm").value(DEFAULT_MODIFIED_FORM.toString()));
    }

    @Test
    void getNonExistingFormAnswer() throws Exception {
        // Get the formAnswer
        restFormAnswerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewFormAnswer() throws Exception {
        // Initialize the database
        formAnswerRepository.save(formAnswer);

        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();

        // Update the formAnswer
        FormAnswer updatedFormAnswer = formAnswerRepository.findById(formAnswer.getId()).get();
        updatedFormAnswer.creationForm(UPDATED_CREATION_FORM).modifiedForm(UPDATED_MODIFIED_FORM);

        restFormAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFormAnswer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFormAnswer))
            )
            .andExpect(status().isOk());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
        FormAnswer testFormAnswer = formAnswerList.get(formAnswerList.size() - 1);
        assertThat(testFormAnswer.getCreationForm()).isEqualTo(UPDATED_CREATION_FORM);
        assertThat(testFormAnswer.getModifiedForm()).isEqualTo(UPDATED_MODIFIED_FORM);
    }

    @Test
    void putNonExistingFormAnswer() throws Exception {
        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();
        formAnswer.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, formAnswer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFormAnswer() throws Exception {
        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();
        formAnswer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormAnswerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(formAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFormAnswer() throws Exception {
        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();
        formAnswer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormAnswerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(formAnswer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFormAnswerWithPatch() throws Exception {
        // Initialize the database
        formAnswerRepository.save(formAnswer);

        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();

        // Update the formAnswer using partial update
        FormAnswer partialUpdatedFormAnswer = new FormAnswer();
        partialUpdatedFormAnswer.setId(formAnswer.getId());

        partialUpdatedFormAnswer.creationForm(UPDATED_CREATION_FORM).modifiedForm(UPDATED_MODIFIED_FORM);

        restFormAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormAnswer))
            )
            .andExpect(status().isOk());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
        FormAnswer testFormAnswer = formAnswerList.get(formAnswerList.size() - 1);
        assertThat(testFormAnswer.getCreationForm()).isEqualTo(UPDATED_CREATION_FORM);
        assertThat(testFormAnswer.getModifiedForm()).isEqualTo(UPDATED_MODIFIED_FORM);
    }

    @Test
    void fullUpdateFormAnswerWithPatch() throws Exception {
        // Initialize the database
        formAnswerRepository.save(formAnswer);

        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();

        // Update the formAnswer using partial update
        FormAnswer partialUpdatedFormAnswer = new FormAnswer();
        partialUpdatedFormAnswer.setId(formAnswer.getId());

        partialUpdatedFormAnswer.creationForm(UPDATED_CREATION_FORM).modifiedForm(UPDATED_MODIFIED_FORM);

        restFormAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFormAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFormAnswer))
            )
            .andExpect(status().isOk());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
        FormAnswer testFormAnswer = formAnswerList.get(formAnswerList.size() - 1);
        assertThat(testFormAnswer.getCreationForm()).isEqualTo(UPDATED_CREATION_FORM);
        assertThat(testFormAnswer.getModifiedForm()).isEqualTo(UPDATED_MODIFIED_FORM);
    }

    @Test
    void patchNonExistingFormAnswer() throws Exception {
        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();
        formAnswer.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, formAnswer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFormAnswer() throws Exception {
        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();
        formAnswer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(formAnswer))
            )
            .andExpect(status().isBadRequest());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFormAnswer() throws Exception {
        int databaseSizeBeforeUpdate = formAnswerRepository.findAll().size();
        formAnswer.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormAnswerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(formAnswer))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FormAnswer in the database
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFormAnswer() throws Exception {
        // Initialize the database
        formAnswerRepository.save(formAnswer);

        int databaseSizeBeforeDelete = formAnswerRepository.findAll().size();

        // Delete the formAnswer
        restFormAnswerMockMvc
            .perform(delete(ENTITY_API_URL_ID, formAnswer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FormAnswer> formAnswerList = formAnswerRepository.findAll();
        assertThat(formAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
