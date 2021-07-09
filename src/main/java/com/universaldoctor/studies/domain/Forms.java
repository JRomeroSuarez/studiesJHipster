package com.universaldoctor.studies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Forms.
 */
@Document(collection = "forms")
public class Forms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("num_responses")
    private Integer numResponses;

    @DBRef
    @Field("questions")
    @JsonIgnoreProperties(value = { "answers", "forms" }, allowSetters = true)
    private Set<Questions> questions = new HashSet<>();

    @DBRef
    @Field("formAnswer")
    @JsonIgnoreProperties(value = { "answers", "forms" }, allowSetters = true)
    private Set<FormAnswer> formAnswers = new HashSet<>();

    @DBRef
    @Field("study")
    @JsonIgnoreProperties(value = { "forms", "participants" }, allowSetters = true)
    private Study study;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Forms id(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Forms title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Forms description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumResponses() {
        return this.numResponses;
    }

    public Forms numResponses(Integer numResponses) {
        this.numResponses = numResponses;
        return this;
    }

    public void setNumResponses(Integer numResponses) {
        this.numResponses = numResponses;
    }

    public Set<Questions> getQuestions() {
        return this.questions;
    }

    public Forms questions(Set<Questions> questions) {
        this.setQuestions(questions);
        return this;
    }

    public Forms addQuestions(Questions questions) {
        this.questions.add(questions);
        questions.setForms(this);
        return this;
    }

    public Forms removeQuestions(Questions questions) {
        this.questions.remove(questions);
        questions.setForms(null);
        return this;
    }

    public void setQuestions(Set<Questions> questions) {
        if (this.questions != null) {
            this.questions.forEach(i -> i.setForms(null));
        }
        if (questions != null) {
            questions.forEach(i -> i.setForms(this));
        }
        this.questions = questions;
    }

    public Set<FormAnswer> getFormAnswers() {
        return this.formAnswers;
    }

    public Forms formAnswers(Set<FormAnswer> formAnswers) {
        this.setFormAnswers(formAnswers);
        return this;
    }

    public Forms addFormAnswer(FormAnswer formAnswer) {
        this.formAnswers.add(formAnswer);
        formAnswer.setForms(this);
        return this;
    }

    public Forms removeFormAnswer(FormAnswer formAnswer) {
        this.formAnswers.remove(formAnswer);
        formAnswer.setForms(null);
        return this;
    }

    public void setFormAnswers(Set<FormAnswer> formAnswers) {
        if (this.formAnswers != null) {
            this.formAnswers.forEach(i -> i.setForms(null));
        }
        if (formAnswers != null) {
            formAnswers.forEach(i -> i.setForms(this));
        }
        this.formAnswers = formAnswers;
    }

    public Study getStudy() {
        return this.study;
    }

    public Forms study(Study study) {
        this.setStudy(study);
        return this;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Forms)) {
            return false;
        }
        return id != null && id.equals(((Forms) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Forms{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", numResponses=" + getNumResponses() +
            "}";
    }
}
