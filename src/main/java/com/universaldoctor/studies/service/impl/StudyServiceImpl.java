package com.universaldoctor.studies.service.impl;

import com.universaldoctor.studies.domain.Study;
import com.universaldoctor.studies.repository.StudyRepository;
import com.universaldoctor.studies.service.StudyService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class StudyServiceImpl implements StudyService {

    private final Logger log = LoggerFactory.getLogger(StudyServiceImpl.class);

    private final StudyRepository studyRepository;

    public StudyServiceImpl(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    /**
     * Save a study.
     *
     * @param study the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Study save(Study study) {
        log.debug("Request to save Study : {}", study);
        return studyRepository.save(study);
    }

    /**
     * Get all the studies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Study> findAll(Pageable pageable) {
        log.debug("Request to get all Studies");
        return studyRepository.findAll(pageable);
    }

    /**
     * Get one study by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Study> findOne(String id) {
        log.debug("Request to get Study : {}", id);
        return studyRepository.findById(id);
    }

    /**
     * Delete the study by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Study : {}", id);
        studyRepository.deleteById(id);
    }
}
