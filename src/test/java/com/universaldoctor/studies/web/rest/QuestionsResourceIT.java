package com.universaldoctor.studies.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universaldoctor.studies.IntegrationTest;
import com.universaldoctor.studies.domain.Questions;
import com.universaldoctor.studies.domain.enumeration.FieldType;
import com.universaldoctor.studies.repository.QuestionsRepository;
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
 * Integration tests for the {@link QuestionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionsResourceIT {

    private static final String DEFAULT_SUBTITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final FieldType DEFAULT_FIELD_TYPE = FieldType.INFO;
    private static final FieldType UPDATED_FIELD_TYPE = FieldType.FREE_TEXT;

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final String DEFAULT_VARIABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VARIABLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CONDITIONAL = false;
    private static final Boolean UPDATED_CONDITIONAL = true;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EDIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EDIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ACTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private MockMvc restQuestionsMockMvc;

    private Questions questions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createEntity() {
        Questions questions = new Questions()
            .subtitle(DEFAULT_SUBTITLE)
            .info(DEFAULT_INFO)
            .fieldType(DEFAULT_FIELD_TYPE)
            .mandatory(DEFAULT_MANDATORY)
            .variableName(DEFAULT_VARIABLE_NAME)
            .units(DEFAULT_UNITS)
            .conditional(DEFAULT_CONDITIONAL)
            .creationDate(DEFAULT_CREATION_DATE)
            .editDate(DEFAULT_EDIT_DATE)
            .actions(DEFAULT_ACTIONS);
        return questions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questions createUpdatedEntity() {
        Questions questions = new Questions()
            .subtitle(UPDATED_SUBTITLE)
            .info(UPDATED_INFO)
            .fieldType(UPDATED_FIELD_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .variableName(UPDATED_VARIABLE_NAME)
            .units(UPDATED_UNITS)
            .conditional(UPDATED_CONDITIONAL)
            .creationDate(UPDATED_CREATION_DATE)
            .editDate(UPDATED_EDIT_DATE)
            .actions(UPDATED_ACTIONS);
        return questions;
    }

    @BeforeEach
    public void initTest() {
        questionsRepository.deleteAll();
        questions = createEntity();
    }

    @Test
    void createQuestions() throws Exception {
        int databaseSizeBeforeCreate = questionsRepository.findAll().size();
        // Create the Questions
        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questions)))
            .andExpect(status().isCreated());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate + 1);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getSubtitle()).isEqualTo(DEFAULT_SUBTITLE);
        assertThat(testQuestions.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testQuestions.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testQuestions.getMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testQuestions.getVariableName()).isEqualTo(DEFAULT_VARIABLE_NAME);
        assertThat(testQuestions.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testQuestions.getConditional()).isEqualTo(DEFAULT_CONDITIONAL);
        assertThat(testQuestions.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testQuestions.getEditDate()).isEqualTo(DEFAULT_EDIT_DATE);
        assertThat(testQuestions.getActions()).isEqualTo(DEFAULT_ACTIONS);
    }

    @Test
    void createQuestionsWithExistingId() throws Exception {
        // Create the Questions with an existing ID
        questions.setId("existing_id");

        int databaseSizeBeforeCreate = questionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questions)))
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllQuestions() throws Exception {
        // Initialize the database
        questionsRepository.save(questions);

        // Get all the questionsList
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questions.getId())))
            .andExpect(jsonPath("$.[*].subtitle").value(hasItem(DEFAULT_SUBTITLE)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].variableName").value(hasItem(DEFAULT_VARIABLE_NAME)))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS)))
            .andExpect(jsonPath("$.[*].conditional").value(hasItem(DEFAULT_CONDITIONAL.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].editDate").value(hasItem(DEFAULT_EDIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].actions").value(hasItem(DEFAULT_ACTIONS)));
    }

    @Test
    void getQuestions() throws Exception {
        // Initialize the database
        questionsRepository.save(questions);

        // Get the questions
        restQuestionsMockMvc
            .perform(get(ENTITY_API_URL_ID, questions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questions.getId()))
            .andExpect(jsonPath("$.subtitle").value(DEFAULT_SUBTITLE))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.variableName").value(DEFAULT_VARIABLE_NAME))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS))
            .andExpect(jsonPath("$.conditional").value(DEFAULT_CONDITIONAL.booleanValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.editDate").value(DEFAULT_EDIT_DATE.toString()))
            .andExpect(jsonPath("$.actions").value(DEFAULT_ACTIONS));
    }

    @Test
    void getNonExistingQuestions() throws Exception {
        // Get the questions
        restQuestionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewQuestions() throws Exception {
        // Initialize the database
        questionsRepository.save(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions
        Questions updatedQuestions = questionsRepository.findById(questions.getId()).get();
        updatedQuestions
            .subtitle(UPDATED_SUBTITLE)
            .info(UPDATED_INFO)
            .fieldType(UPDATED_FIELD_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .variableName(UPDATED_VARIABLE_NAME)
            .units(UPDATED_UNITS)
            .conditional(UPDATED_CONDITIONAL)
            .creationDate(UPDATED_CREATION_DATE)
            .editDate(UPDATED_EDIT_DATE)
            .actions(UPDATED_ACTIONS);

        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedQuestions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);
        assertThat(testQuestions.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testQuestions.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testQuestions.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testQuestions.getVariableName()).isEqualTo(UPDATED_VARIABLE_NAME);
        assertThat(testQuestions.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testQuestions.getConditional()).isEqualTo(UPDATED_CONDITIONAL);
        assertThat(testQuestions.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testQuestions.getEditDate()).isEqualTo(UPDATED_EDIT_DATE);
        assertThat(testQuestions.getActions()).isEqualTo(UPDATED_ACTIONS);
    }

    @Test
    void putNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateQuestionsWithPatch() throws Exception {
        // Initialize the database
        questionsRepository.save(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions using partial update
        Questions partialUpdatedQuestions = new Questions();
        partialUpdatedQuestions.setId(questions.getId());

        partialUpdatedQuestions
            .subtitle(UPDATED_SUBTITLE)
            .fieldType(UPDATED_FIELD_TYPE)
            .variableName(UPDATED_VARIABLE_NAME)
            .units(UPDATED_UNITS)
            .conditional(UPDATED_CONDITIONAL)
            .creationDate(UPDATED_CREATION_DATE)
            .editDate(UPDATED_EDIT_DATE)
            .actions(UPDATED_ACTIONS);

        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);
        assertThat(testQuestions.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testQuestions.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testQuestions.getMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testQuestions.getVariableName()).isEqualTo(UPDATED_VARIABLE_NAME);
        assertThat(testQuestions.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testQuestions.getConditional()).isEqualTo(UPDATED_CONDITIONAL);
        assertThat(testQuestions.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testQuestions.getEditDate()).isEqualTo(UPDATED_EDIT_DATE);
        assertThat(testQuestions.getActions()).isEqualTo(UPDATED_ACTIONS);
    }

    @Test
    void fullUpdateQuestionsWithPatch() throws Exception {
        // Initialize the database
        questionsRepository.save(questions);

        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();

        // Update the questions using partial update
        Questions partialUpdatedQuestions = new Questions();
        partialUpdatedQuestions.setId(questions.getId());

        partialUpdatedQuestions
            .subtitle(UPDATED_SUBTITLE)
            .info(UPDATED_INFO)
            .fieldType(UPDATED_FIELD_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .variableName(UPDATED_VARIABLE_NAME)
            .units(UPDATED_UNITS)
            .conditional(UPDATED_CONDITIONAL)
            .creationDate(UPDATED_CREATION_DATE)
            .editDate(UPDATED_EDIT_DATE)
            .actions(UPDATED_ACTIONS);

        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestions))
            )
            .andExpect(status().isOk());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
        Questions testQuestions = questionsList.get(questionsList.size() - 1);
        assertThat(testQuestions.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);
        assertThat(testQuestions.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testQuestions.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testQuestions.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testQuestions.getVariableName()).isEqualTo(UPDATED_VARIABLE_NAME);
        assertThat(testQuestions.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testQuestions.getConditional()).isEqualTo(UPDATED_CONDITIONAL);
        assertThat(testQuestions.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testQuestions.getEditDate()).isEqualTo(UPDATED_EDIT_DATE);
        assertThat(testQuestions.getActions()).isEqualTo(UPDATED_ACTIONS);
    }

    @Test
    void patchNonExistingQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isBadRequest());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamQuestions() throws Exception {
        int databaseSizeBeforeUpdate = questionsRepository.findAll().size();
        questions.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(questions))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Questions in the database
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteQuestions() throws Exception {
        // Initialize the database
        questionsRepository.save(questions);

        int databaseSizeBeforeDelete = questionsRepository.findAll().size();

        // Delete the questions
        restQuestionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, questions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questions> questionsList = questionsRepository.findAll();
        assertThat(questionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
