package com.universaldoctor.studies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A FormAnswer.
 */
@Document(collection = "form_answer")
public class FormAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("creation_form")
    private Instant creationForm;

    @Field("modified_form")
    private Instant modifiedForm;

    @DBRef
    @Field("answer")
    @JsonIgnoreProperties(value = { "questions", "formAnswer" }, allowSetters = true)
    private Set<Answer> answers = new HashSet<>();

    @DBRef
    @Field("forms")
    @JsonIgnoreProperties(value = { "questions", "formAnswers", "study" }, allowSetters = true)
    private Forms forms;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormAnswer id(String id) {
        this.id = id;
        return this;
    }

    public Instant getCreationForm() {
        return this.creationForm;
    }

    public FormAnswer creationForm(Instant creationForm) {
        this.creationForm = creationForm;
        return this;
    }

    public void setCreationForm(Instant creationForm) {
        this.creationForm = creationForm;
    }

    public Instant getModifiedForm() {
        return this.modifiedForm;
    }

    public FormAnswer modifiedForm(Instant modifiedForm) {
        this.modifiedForm = modifiedForm;
        return this;
    }

    public void setModifiedForm(Instant modifiedForm) {
        this.modifiedForm = modifiedForm;
    }

    public Set<Answer> getAnswers() {
        return this.answers;
    }

    public FormAnswer answers(Set<Answer> answers) {
        this.setAnswers(answers);
        return this;
    }

    public FormAnswer addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setFormAnswer(this);
        return this;
    }

    public FormAnswer removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setFormAnswer(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        if (this.answers != null) {
            this.answers.forEach(i -> i.setFormAnswer(null));
        }
        if (answers != null) {
            answers.forEach(i -> i.setFormAnswer(this));
        }
        this.answers = answers;
    }

    public Forms getForms() {
        return this.forms;
    }

    public FormAnswer forms(Forms forms) {
        this.setForms(forms);
        return this;
    }

    public void setForms(Forms forms) {
        this.forms = forms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormAnswer)) {
            return false;
        }
        return id != null && id.equals(((FormAnswer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormAnswer{" +
            "id=" + getId() +
            ", creationForm='" + getCreationForm() + "'" +
            ", modifiedForm='" + getModifiedForm() + "'" +
            "}";
    }
}
