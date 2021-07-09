package com.universaldoctor.studies.service.impl;

import com.universaldoctor.studies.domain.Questions;
import com.universaldoctor.studies.repository.QuestionsRepository;
import com.universaldoctor.studies.service.QuestionsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class QuestionsServiceImpl implements QuestionsService {

    private final Logger log = LoggerFactory.getLogger(QuestionsServiceImpl.class);

    private final QuestionsRepository questionsRepository;

    public QuestionsServiceImpl(QuestionsRepository questionsRepository) {
        this.questionsRepository = questionsRepository;
    }

    /**
     * Save a participant.
     *
     * @param questions the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Questions save(Questions questions) {
        log.debug("Request to save Participant : {}", questions);
        return questionsRepository.save(questions);
    }

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Questions> findAll(Pageable pageable) {
        log.debug("Request to get all Participants");
        return questionsRepository.findAll(pageable);
    }

    /**
     * Get one participant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Questions> findOne(String id) {
        log.debug("Request to get Participant : {}", id);
        return questionsRepository.findById(id);
    }

    /**
     * Delete the participant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Participant : {}", id);
        questionsRepository.deleteById(id);
    }
}
