package com.universaldoctor.studies.repository;

import com.universaldoctor.studies.domain.Participants;
import com.universaldoctor.studies.domain.Study;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Participants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipantsRepository extends MongoRepository<Participants, String> {
    Optional<Set<Participants>> findAllByStudy(Study study);
}
