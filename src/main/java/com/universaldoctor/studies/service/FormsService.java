package com.universaldoctor.studies.service;

import com.universaldoctor.studies.domain.Forms;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FormsService {
    /**
     * Save a Forms.
     *
     * @param forms the entity to save.
     * @return the persisted entity.
     */
    Forms save(Forms forms);

    /**
     * Get all the Forms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Forms> findAll(Pageable pageable);

    /**
     * Get the "id" Forms.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Forms> findOne(String id);

    /**
     * Delete the "id" Forms.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
