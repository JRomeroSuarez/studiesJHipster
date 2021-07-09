package com.universaldoctor.studies.web.rest;

import com.universaldoctor.studies.domain.Participants;
import com.universaldoctor.studies.repository.ParticipantsRepository;
import com.universaldoctor.studies.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.universaldoctor.studies.domain.Participants}.
 */
@RestController
@RequestMapping("/api")
public class ParticipantsResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantsResource.class);

    private static final String ENTITY_NAME = "participants";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantsRepository participantsRepository;

    public ParticipantsResource(ParticipantsRepository participantsRepository) {
        this.participantsRepository = participantsRepository;
    }

    /**
     * {@code POST  /participants} : Create a new participants.
     *
     * @param participants the participants to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participants, or with status {@code 400 (Bad Request)} if the participants has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participants")
    public ResponseEntity<Participants> createParticipants(@RequestBody Participants participants) throws URISyntaxException {
        log.debug("REST request to save Participants : {}", participants);
        if (participants.getId() != null) {
            throw new BadRequestAlertException("A new participants cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Participants result = participantsRepository.save(participants);
        return ResponseEntity
            .created(new URI("/api/participants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /participants/:id} : Updates an existing participants.
     *
     * @param id the id of the participants to save.
     * @param participants the participants to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participants,
     * or with status {@code 400 (Bad Request)} if the participants is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participants couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participants/{id}")
    public ResponseEntity<Participants> updateParticipants(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Participants participants
    ) throws URISyntaxException {
        log.debug("REST request to update Participants : {}, {}", id, participants);
        if (participants.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participants.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Participants result = participantsRepository.save(participants);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participants.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /participants/:id} : Partial updates given fields of an existing participants, field will ignore if it is null
     *
     * @param id the id of the participants to save.
     * @param participants the participants to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participants,
     * or with status {@code 400 (Bad Request)} if the participants is not valid,
     * or with status {@code 404 (Not Found)} if the participants is not found,
     * or with status {@code 500 (Internal Server Error)} if the participants couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/participants/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Participants> partialUpdateParticipants(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Participants participants
    ) throws URISyntaxException {
        log.debug("REST request to partial update Participants partially : {}, {}", id, participants);
        if (participants.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, participants.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!participantsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Participants> result = participantsRepository
            .findById(participants.getId())
            .map(
                existingParticipants -> {
                    if (participants.getEmail() != null) {
                        existingParticipants.setEmail(participants.getEmail());
                    }
                    if (participants.getAssociatedForms() != null) {
                        existingParticipants.setAssociatedForms(participants.getAssociatedForms());
                    }
                    if (participants.getFormsCompleted() != null) {
                        existingParticipants.setFormsCompleted(participants.getFormsCompleted());
                    }
                    if (participants.getLanguaje() != null) {
                        existingParticipants.setLanguaje(participants.getLanguaje());
                    }
                    if (participants.getInvitationStatus() != null) {
                        existingParticipants.setInvitationStatus(participants.getInvitationStatus());
                    }
                    if (participants.getActions() != null) {
                        existingParticipants.setActions(participants.getActions());
                    }

                    return existingParticipants;
                }
            )
            .map(participantsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participants.getId())
        );
    }

    /**
     * {@code GET  /participants} : get all the participants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participants in body.
     */
    @GetMapping("/participants")
    public List<Participants> getAllParticipants() {
        log.debug("REST request to get all Participants");
        return participantsRepository.findAll();
    }

    /**
     * {@code GET  /participants/:id} : get the "id" participants.
     *
     * @param id the id of the participants to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participants, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participants/{id}")
    public ResponseEntity<Participants> getParticipants(@PathVariable String id) {
        log.debug("REST request to get Participants : {}", id);
        Optional<Participants> participants = participantsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(participants);
    }

    /**
     * {@code DELETE  /participants/:id} : delete the "id" participants.
     *
     * @param id the id of the participants to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participants/{id}")
    public ResponseEntity<Void> deleteParticipants(@PathVariable String id) {
        log.debug("REST request to delete Participants : {}", id);
        participantsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
