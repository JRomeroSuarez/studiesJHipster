package com.universaldoctor.studies.repository;

import com.universaldoctor.studies.domain.Forms;
import com.universaldoctor.studies.domain.Study;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Forms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormsRepository extends MongoRepository<Forms, String> {
    Optional<Set<Forms>> findAllByStudy(Study study);
}
