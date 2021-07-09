package com.universaldoctor.studies.repository;

import com.universaldoctor.studies.domain.Participants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Participants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantsRepository extends MongoRepository<Participants, String> {}
