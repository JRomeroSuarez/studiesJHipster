package com.universaldoctor.studies.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.universaldoctor.studies.IntegrationTest;
import com.universaldoctor.studies.domain.Participants;
import com.universaldoctor.studies.domain.enumeration.Status;
import com.universaldoctor.studies.repository.ParticipantsRepository;
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
 * Integration tests for the {@link ParticipantsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParticipantsResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ASSOCIATED_FORMS = "AAAAAAAAAA";
    private static final String UPDATED_ASSOCIATED_FORMS = "BBBBBBBBBB";

    private static final Integer DEFAULT_FORMS_COMPLETED = 1;
    private static final Integer UPDATED_FORMS_COMPLETED = 2;

    private static final String DEFAULT_LANGUAJE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAJE = "BBBBBBBBBB";

    private static final Status DEFAULT_INVITATION_STATUS = Status.ACCEPTED;
    private static final Status UPDATED_INVITATION_STATUS = Status.PENDING;

    private static final String DEFAULT_ACTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ACTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/participants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ParticipantsRepository participantsRepository;

    @Autowired
    private MockMvc restParticipantsMockMvc;

    private Participants participants;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participants createEntity() {
        Participants participants = new Participants()
            .email(DEFAULT_EMAIL)
            .associatedForms(DEFAULT_ASSOCIATED_FORMS)
            .formsCompleted(DEFAULT_FORMS_COMPLETED)
            .languaje(DEFAULT_LANGUAJE)
            .invitationStatus(DEFAULT_INVITATION_STATUS)
            .actions(DEFAULT_ACTIONS);
        return participants;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participants createUpdatedEntity() {
        Participants participants = new Participants()
            .email(UPDATED_EMAIL)
            .associatedForms(UPDATED_ASSOCIATED_FORMS)
            .formsCompleted(UPDATED_FORMS_COMPLETED)
            .languaje(UPDATED_LANGUAJE)
            .invitationStatus(UPDATED_INVITATION_STATUS)
            .actions(UPDATED_ACTIONS);
        return participants;
    }

    @BeforeEach
    public void initTest() {
        participantsRepository.deleteAll();
        participants = createEntity();
    }

    @Test
    void createParticipants() throws Exception {
        int databaseSizeBeforeCreate = participantsRepository.findAll().size();
        // Create the Participants
        restParticipantsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participants)))
            .andExpect(status().isCreated());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeCreate + 1);
        Participants testParticipants = participantsList.get(participantsList.size() - 1);
        assertThat(testParticipants.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParticipants.getAssociatedForms()).isEqualTo(DEFAULT_ASSOCIATED_FORMS);
        assertThat(testParticipants.getFormsCompleted()).isEqualTo(DEFAULT_FORMS_COMPLETED);
        assertThat(testParticipants.getLanguaje()).isEqualTo(DEFAULT_LANGUAJE);
        assertThat(testParticipants.getInvitationStatus()).isEqualTo(DEFAULT_INVITATION_STATUS);
        assertThat(testParticipants.getActions()).isEqualTo(DEFAULT_ACTIONS);
    }

    @Test
    void createParticipantsWithExistingId() throws Exception {
        // Create the Participants with an existing ID
        participants.setId("existing_id");

        int databaseSizeBeforeCreate = participantsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipantsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participants)))
            .andExpect(status().isBadRequest());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllParticipants() throws Exception {
        // Initialize the database
        participantsRepository.save(participants);

        // Get all the participantsList
        restParticipantsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participants.getId())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].associatedForms").value(hasItem(DEFAULT_ASSOCIATED_FORMS)))
            .andExpect(jsonPath("$.[*].formsCompleted").value(hasItem(DEFAULT_FORMS_COMPLETED)))
            .andExpect(jsonPath("$.[*].languaje").value(hasItem(DEFAULT_LANGUAJE)))
            .andExpect(jsonPath("$.[*].invitationStatus").value(hasItem(DEFAULT_INVITATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].actions").value(hasItem(DEFAULT_ACTIONS)));
    }

    @Test
    void getParticipants() throws Exception {
        // Initialize the database
        participantsRepository.save(participants);

        // Get the participants
        restParticipantsMockMvc
            .perform(get(ENTITY_API_URL_ID, participants.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(participants.getId()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.associatedForms").value(DEFAULT_ASSOCIATED_FORMS))
            .andExpect(jsonPath("$.formsCompleted").value(DEFAULT_FORMS_COMPLETED))
            .andExpect(jsonPath("$.languaje").value(DEFAULT_LANGUAJE))
            .andExpect(jsonPath("$.invitationStatus").value(DEFAULT_INVITATION_STATUS.toString()))
            .andExpect(jsonPath("$.actions").value(DEFAULT_ACTIONS));
    }

    @Test
    void getNonExistingParticipants() throws Exception {
        // Get the participants
        restParticipantsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewParticipants() throws Exception {
        // Initialize the database
        participantsRepository.save(participants);

        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();

        // Update the participants
        Participants updatedParticipants = participantsRepository.findById(participants.getId()).get();
        updatedParticipants
            .email(UPDATED_EMAIL)
            .associatedForms(UPDATED_ASSOCIATED_FORMS)
            .formsCompleted(UPDATED_FORMS_COMPLETED)
            .languaje(UPDATED_LANGUAJE)
            .invitationStatus(UPDATED_INVITATION_STATUS)
            .actions(UPDATED_ACTIONS);

        restParticipantsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedParticipants.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedParticipants))
            )
            .andExpect(status().isOk());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
        Participants testParticipants = participantsList.get(participantsList.size() - 1);
        assertThat(testParticipants.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParticipants.getAssociatedForms()).isEqualTo(UPDATED_ASSOCIATED_FORMS);
        assertThat(testParticipants.getFormsCompleted()).isEqualTo(UPDATED_FORMS_COMPLETED);
        assertThat(testParticipants.getLanguaje()).isEqualTo(UPDATED_LANGUAJE);
        assertThat(testParticipants.getInvitationStatus()).isEqualTo(UPDATED_INVITATION_STATUS);
        assertThat(testParticipants.getActions()).isEqualTo(UPDATED_ACTIONS);
    }

    @Test
    void putNonExistingParticipants() throws Exception {
        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();
        participants.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, participants.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participants))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchParticipants() throws Exception {
        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();
        participants.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(participants))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamParticipants() throws Exception {
        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();
        participants.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(participants)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateParticipantsWithPatch() throws Exception {
        // Initialize the database
        participantsRepository.save(participants);

        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();

        // Update the participants using partial update
        Participants partialUpdatedParticipants = new Participants();
        partialUpdatedParticipants.setId(participants.getId());

        partialUpdatedParticipants.associatedForms(UPDATED_ASSOCIATED_FORMS).formsCompleted(UPDATED_FORMS_COMPLETED);

        restParticipantsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipants.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipants))
            )
            .andExpect(status().isOk());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
        Participants testParticipants = participantsList.get(participantsList.size() - 1);
        assertThat(testParticipants.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParticipants.getAssociatedForms()).isEqualTo(UPDATED_ASSOCIATED_FORMS);
        assertThat(testParticipants.getFormsCompleted()).isEqualTo(UPDATED_FORMS_COMPLETED);
        assertThat(testParticipants.getLanguaje()).isEqualTo(DEFAULT_LANGUAJE);
        assertThat(testParticipants.getInvitationStatus()).isEqualTo(DEFAULT_INVITATION_STATUS);
        assertThat(testParticipants.getActions()).isEqualTo(DEFAULT_ACTIONS);
    }

    @Test
    void fullUpdateParticipantsWithPatch() throws Exception {
        // Initialize the database
        participantsRepository.save(participants);

        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();

        // Update the participants using partial update
        Participants partialUpdatedParticipants = new Participants();
        partialUpdatedParticipants.setId(participants.getId());

        partialUpdatedParticipants
            .email(UPDATED_EMAIL)
            .associatedForms(UPDATED_ASSOCIATED_FORMS)
            .formsCompleted(UPDATED_FORMS_COMPLETED)
            .languaje(UPDATED_LANGUAJE)
            .invitationStatus(UPDATED_INVITATION_STATUS)
            .actions(UPDATED_ACTIONS);

        restParticipantsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParticipants.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParticipants))
            )
            .andExpect(status().isOk());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
        Participants testParticipants = participantsList.get(participantsList.size() - 1);
        assertThat(testParticipants.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParticipants.getAssociatedForms()).isEqualTo(UPDATED_ASSOCIATED_FORMS);
        assertThat(testParticipants.getFormsCompleted()).isEqualTo(UPDATED_FORMS_COMPLETED);
        assertThat(testParticipants.getLanguaje()).isEqualTo(UPDATED_LANGUAJE);
        assertThat(testParticipants.getInvitationStatus()).isEqualTo(UPDATED_INVITATION_STATUS);
        assertThat(testParticipants.getActions()).isEqualTo(UPDATED_ACTIONS);
    }

    @Test
    void patchNonExistingParticipants() throws Exception {
        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();
        participants.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipantsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, participants.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participants))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchParticipants() throws Exception {
        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();
        participants.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(participants))
            )
            .andExpect(status().isBadRequest());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamParticipants() throws Exception {
        int databaseSizeBeforeUpdate = participantsRepository.findAll().size();
        participants.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParticipantsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(participants))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Participants in the database
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteParticipants() throws Exception {
        // Initialize the database
        participantsRepository.save(participants);

        int databaseSizeBeforeDelete = participantsRepository.findAll().size();

        // Delete the participants
        restParticipantsMockMvc
            .perform(delete(ENTITY_API_URL_ID, participants.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participants> participantsList = participantsRepository.findAll();
        assertThat(participantsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
