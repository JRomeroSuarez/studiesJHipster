package com.universaldoctor.studies.service.impl;

import com.universaldoctor.studies.domain.Forms;
import com.universaldoctor.studies.repository.FormsRepository;
import com.universaldoctor.studies.service.FormsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FormsServiceImpl implements FormsService {

    private final Logger log = LoggerFactory.getLogger(ParticipantsServiceImpl.class);

    private final FormsRepository formsRepository;

    public FormsServiceImpl(FormsRepository formsRepository) {
        this.formsRepository = formsRepository;
    }

    /**
     * Save a participant.
     *
     * @param forms the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Forms save(Forms forms) {
        log.debug("Request to save Forms : {}", forms);
        return formsRepository.save(forms);
    }

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Forms> findAll(Pageable pageable) {
        log.debug("Request to get all Participants");
        return formsRepository.findAll(pageable);
    }

    /**
     * Get one participant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Forms> findOne(String id) {
        log.debug("Request to get Participant : {}", id);
        return formsRepository.findById(id);
    }

    /**
     * Delete the participant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Participant : {}", id);
        formsRepository.deleteById(id);
    }
}
