package com.universaldoctor.studies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Answer.
 */
@Document(collection = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("answer")
    private String answer;

    @DBRef
    @Field("questions")
    @JsonIgnoreProperties(value = { "answers", "forms" }, allowSetters = true)
    private Questions questions;

    @DBRef
    @Field("formAnswer")
    @JsonIgnoreProperties(value = { "answers", "forms" }, allowSetters = true)
    private FormAnswer formAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Answer id(String id) {
        this.id = id;
        return this;
    }

    public String getAnswer() {
        return this.answer;
    }

    public Answer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Questions getQuestions() {
        return this.questions;
    }

    public Answer questions(Questions questions) {
        this.setQuestions(questions);
        return this;
    }

    public void setQuestions(Questions questions) {
        this.questions = questions;
    }

    public FormAnswer getFormAnswer() {
        return this.formAnswer;
    }

    public Answer formAnswer(FormAnswer formAnswer) {
        this.setFormAnswer(formAnswer);
        return this;
    }

    public void setFormAnswer(FormAnswer formAnswer) {
        this.formAnswer = formAnswer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
