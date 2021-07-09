package com.universaldoctor.studies.service;

import com.universaldoctor.studies.domain.Study;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Study}.
 */
public interface StudyService {
    /**
     * Save a study.
     *
     * @param study the entity to save.
     * @return the persisted entity.
     */
    Study save(Study study);

    /**
     * Get all the studies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Study> findAll(Pageable pageable);

    /**
     * Get the "id" study.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Study> findOne(String id);

    /**
     * Delete the "id" study.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
