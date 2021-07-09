package com.universaldoctor.studies.web.rest;

import com.universaldoctor.studies.domain.Forms;
import com.universaldoctor.studies.domain.Questions;
import com.universaldoctor.studies.repository.FormsRepository;
import com.universaldoctor.studies.repository.QuestionsRepository;
import com.universaldoctor.studies.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.universaldoctor.studies.domain.Questions}.
 */
@RestController
@RequestMapping("/api")
public class QuestionsResource {

    private final Logger log = LoggerFactory.getLogger(QuestionsResource.class);

    private static final String ENTITY_NAME = "questions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionsRepository questionsRepository;
    private final FormsRepository formsRepository;

    public QuestionsResource(QuestionsRepository questionsRepository, FormsRepository formsRepository) {
        this.questionsRepository = questionsRepository;
        this.formsRepository = formsRepository;
    }

    /**
     * {@code POST  /questions} : Create a new questions.
     *
     * @param questions the questions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questions, or with status {@code 400 (Bad Request)} if the questions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions")
    public ResponseEntity<Questions> createQuestions(@RequestBody Questions questions) throws URISyntaxException {
        log.debug("REST request to save Questions : {}", questions);
        if (questions.getId() != null) {
            throw new BadRequestAlertException("A new questions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Questions result = questionsRepository.save(questions);
        return ResponseEntity
            .created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /questions/:id} : Updates an existing questions.
     *
     * @param id        the id of the questions to save.
     * @param questions the questions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questions,
     * or with status {@code 400 (Bad Request)} if the questions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions/{id}")
    public ResponseEntity<Questions> updateQuestions(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Questions questions
    ) throws URISyntaxException {
        log.debug("REST request to update Questions : {}, {}", id, questions);
        if (questions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Questions result = questionsRepository.save(questions);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questions.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /questions/:id} : Partial updates given fields of an existing questions, field will ignore if it is null
     *
     * @param id        the id of the questions to save.
     * @param questions the questions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questions,
     * or with status {@code 400 (Bad Request)} if the questions is not valid,
     * or with status {@code 404 (Not Found)} if the questions is not found,
     * or with status {@code 500 (Internal Server Error)} if the questions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/questions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Questions> partialUpdateQuestions(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Questions questions
    ) throws URISyntaxException {
        log.debug("REST request to partial update Questions partially : {}, {}", id, questions);
        if (questions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, questions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!questionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Questions> result = questionsRepository
            .findById(questions.getId())
            .map(
                existingQuestions -> {
                    if (questions.getSubtitle() != null) {
                        existingQuestions.setSubtitle(questions.getSubtitle());
                    }
                    if (questions.getInfo() != null) {
                        existingQuestions.setInfo(questions.getInfo());
                    }
                    if (questions.getFieldType() != null) {
                        existingQuestions.setFieldType(questions.getFieldType());
                    }
                    if (questions.getMandatory() != null) {
                        existingQuestions.setMandatory(questions.getMandatory());
                    }
                    if (questions.getVariableName() != null) {
                        existingQuestions.setVariableName(questions.getVariableName());
                    }
                    if (questions.getUnits() != null) {
                        existingQuestions.setUnits(questions.getUnits());
                    }
                    if (questions.getConditional() != null) {
                        existingQuestions.setConditional(questions.getConditional());
                    }
                    if (questions.getCreationDate() != null) {
                        existingQuestions.setCreationDate(questions.getCreationDate());
                    }
                    if (questions.getEditDate() != null) {
                        existingQuestions.setEditDate(questions.getEditDate());
                    }
                    if (questions.getActions() != null) {
                        existingQuestions.setActions(questions.getActions());
                    }

                    return existingQuestions;
                }
            )
            .map(questionsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questions.getId())
        );
    }

    /**
     * {@code GET  /questions} : get all the questions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questions in body.
     */
    @GetMapping("/questions")
    public List<Questions> getAllQuestions() {
        log.debug("REST request to get all Questions");
        return questionsRepository.findAll();
    }

    /**
     * {@code GET  /questions/:id} : get the "id" questions.
     *
     * @param id the id of the questions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions/{id}")
    public ResponseEntity<Questions> getQuestions(@PathVariable String id) {
        log.debug("REST request to get Questions : {}", id);
        Optional<Questions> questions = questionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(questions);
    }

    /**
     * {@code DELETE  /questions/:id} : delete the "id" questions.
     *
     * @param id the id of the questions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestions(@PathVariable String id) {
        log.debug("REST request to delete Questions : {}", id);
        questionsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
