package com.universaldoctor.studies.service;

import com.universaldoctor.studies.domain.Questions;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionsService {
    /**
     * Save a Questions.
     *
     * @param questions the entity to save.
     * @return the persisted entity.
     */
    Questions save(Questions questions);

    /**
     * Get all the Questions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Questions> findAll(Pageable pageable);

    /**
     * Get the "id" Questions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Questions> findOne(String id);

    /**
     * Delete the "id" Questions.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
