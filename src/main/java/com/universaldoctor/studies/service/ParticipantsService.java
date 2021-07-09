package com.universaldoctor.studies.service;

import com.universaldoctor.studies.domain.Participants;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Participants}.
 */
public interface ParticipantsService {
    /**
     * Save a Participants.
     *
     * @param Participants the entity to save.
     * @return the persisted entity.
     */
    Participants save(Participants Participants);

    /**
     * Get all the Participants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Participants> findAll(Pageable pageable);

    /**
     * Get the "id" Participants.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Participants> findOne(String id);

    /**
     * Delete the "id" Participants.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
