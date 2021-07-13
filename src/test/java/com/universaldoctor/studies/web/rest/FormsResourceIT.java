package com.universaldoctor.studies.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universaldoctor.studies.IntegrationTest;
import com.universaldoctor.studies.domain.Forms;
import com.universaldoctor.studies.repository.FormsRepository;
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
 * Integration tests for the {@link FormsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FormsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_RESPONSES = 1;
    private static final Integer UPDATED_NUM_RESPONSES = 2;

    private static final String ENTITY_API_URL = "/api/forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FormsRepository formsRepository;

    @Autowired
    private MockMvc restFormsMockMvc;

    private Forms forms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forms createEntity() {
        Forms forms = new Forms().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION).numResponses(DEFAULT_NUM_RESPONSES);
        return forms;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forms createUpdatedEntity() {
        Forms forms = new Forms().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).numResponses(UPDATED_NUM_RESPONSES);
        return forms;
    }

    @BeforeEach
    public void initTest() {
        formsRepository.deleteAll();
        forms = createEntity();
    }

    @Test
    void createForms() throws Exception {
        int databaseSizeBeforeCreate = formsRepository.findAll().size();
        // Create the Forms
        restFormsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(forms)))
            .andExpect(status().isCreated());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeCreate + 1);
        Forms testForms = formsList.get(formsList.size() - 1);
        assertThat(testForms.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testForms.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testForms.getNumResponses()).isEqualTo(DEFAULT_NUM_RESPONSES);
    }

    @Test
    void createFormsWithExistingId() throws Exception {
        // Create the Forms with an existing ID
        forms.setId("existing_id");

        int databaseSizeBeforeCreate = formsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(forms)))
            .andExpect(status().isBadRequest());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllForms() throws Exception {
        // Initialize the database
        formsRepository.save(forms);

        // Get all the formsList
        restFormsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forms.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].numResponses").value(hasItem(DEFAULT_NUM_RESPONSES)));
    }

    @Test
    void getForms() throws Exception {
        // Initialize the database
        formsRepository.save(forms);

        // Get the forms
        restFormsMockMvc
            .perform(get(ENTITY_API_URL_ID, forms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(forms.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.numResponses").value(DEFAULT_NUM_RESPONSES));
    }

    @Test
    void getNonExistingForms() throws Exception {
        // Get the forms
        restFormsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewForms() throws Exception {
        // Initialize the database
        formsRepository.save(forms);

        int databaseSizeBeforeUpdate = formsRepository.findAll().size();

        // Update the forms
        Forms updatedForms = formsRepository.findById(forms.getId()).get();
        updatedForms.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).numResponses(UPDATED_NUM_RESPONSES);

        restFormsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedForms.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedForms))
            )
            .andExpect(status().isOk());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
        Forms testForms = formsList.get(formsList.size() - 1);
        assertThat(testForms.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testForms.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testForms.getNumResponses()).isEqualTo(UPDATED_NUM_RESPONSES);
    }

    @Test
    void putNonExistingForms() throws Exception {
        int databaseSizeBeforeUpdate = formsRepository.findAll().size();
        forms.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, forms.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(forms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchForms() throws Exception {
        int databaseSizeBeforeUpdate = formsRepository.findAll().size();
        forms.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(forms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamForms() throws Exception {
        int databaseSizeBeforeUpdate = formsRepository.findAll().size();
        forms.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(forms)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFormsWithPatch() throws Exception {
        // Initialize the database
        formsRepository.save(forms);

        int databaseSizeBeforeUpdate = formsRepository.findAll().size();

        // Update the forms using partial update
        Forms partialUpdatedForms = new Forms();
        partialUpdatedForms.setId(forms.getId());

        partialUpdatedForms.description(UPDATED_DESCRIPTION).numResponses(UPDATED_NUM_RESPONSES);

        restFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedForms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedForms))
            )
            .andExpect(status().isOk());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
        Forms testForms = formsList.get(formsList.size() - 1);
        assertThat(testForms.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testForms.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testForms.getNumResponses()).isEqualTo(UPDATED_NUM_RESPONSES);
    }

    @Test
    void fullUpdateFormsWithPatch() throws Exception {
        // Initialize the database
        formsRepository.save(forms);

        int databaseSizeBeforeUpdate = formsRepository.findAll().size();

        // Update the forms using partial update
        Forms partialUpdatedForms = new Forms();
        partialUpdatedForms.setId(forms.getId());

        partialUpdatedForms.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION).numResponses(UPDATED_NUM_RESPONSES);

        restFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedForms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedForms))
            )
            .andExpect(status().isOk());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
        Forms testForms = formsList.get(formsList.size() - 1);
        assertThat(testForms.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testForms.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testForms.getNumResponses()).isEqualTo(UPDATED_NUM_RESPONSES);
    }

    @Test
    void patchNonExistingForms() throws Exception {
        int databaseSizeBeforeUpdate = formsRepository.findAll().size();
        forms.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, forms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(forms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchForms() throws Exception {
        int databaseSizeBeforeUpdate = formsRepository.findAll().size();
        forms.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(forms))
            )
            .andExpect(status().isBadRequest());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamForms() throws Exception {
        int databaseSizeBeforeUpdate = formsRepository.findAll().size();
        forms.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFormsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(forms)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Forms in the database
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteForms() throws Exception {
        // Initialize the database
        formsRepository.save(forms);

        int databaseSizeBeforeDelete = formsRepository.findAll().size();

        // Delete the forms
        restFormsMockMvc
            .perform(delete(ENTITY_API_URL_ID, forms.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Forms> formsList = formsRepository.findAll();
        assertThat(formsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
