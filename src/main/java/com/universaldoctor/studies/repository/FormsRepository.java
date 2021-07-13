package com.universaldoctor.studies.repository;

import com.universaldoctor.studies.domain.Forms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Forms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormsRepository extends MongoRepository<Forms, String> {}
