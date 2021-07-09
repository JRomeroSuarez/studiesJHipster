package com.universaldoctor.studies.repository;

import com.universaldoctor.studies.domain.FormAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the FormAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormAnswerRepository extends MongoRepository<FormAnswer, String> {}
