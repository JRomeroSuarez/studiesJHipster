package com.universaldoctor.studies.service.impl;

import com.universaldoctor.studies.domain.Participants;
import com.universaldoctor.studies.repository.ParticipantsRepository;
import com.universaldoctor.studies.service.ParticipantsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ParticipantsServiceImpl implements ParticipantsService {

    private final Logger log = LoggerFactory.getLogger(ParticipantsServiceImpl.class);

    private final ParticipantsRepository participantRepository;

    public ParticipantsServiceImpl(ParticipantsRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    /**
     * Save a participant.
     *
     * @param participant the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Participants save(Participants participant) {
        log.debug("Request to save Participant : {}", participant);
        return participantRepository.save(participant);
    }

    /**
     * Get all the participants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Participants> findAll(Pageable pageable) {
        log.debug("Request to get all Participants");
        return participantRepository.findAll(pageable);
    }

    /**
     * Get one participant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Participants> findOne(String id) {
        log.debug("Request to get Participant : {}", id);
        return participantRepository.findById(id);
    }

    /**
     * Delete the participant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Participant : {}", id);
        participantRepository.deleteById(id);
    }
}
