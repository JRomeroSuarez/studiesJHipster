package com.universaldoctor.studies.repository;

import com.universaldoctor.studies.domain.Forms;
import com.universaldoctor.studies.domain.Questions;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Questions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionsRepository extends MongoRepository<Questions, String> {
    Optional<Set<Questions>> findAllByForms(Forms form);
}
