package com.universaldoctor.studies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.universaldoctor.studies.domain.enumeration.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Questions.
 */
@Document(collection = "questions")
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question")
    private String question;

    @Field("subtitle")
    private String subtitle;

    @Field("info")
    private String info;

    @Field("field_type")
    private FieldType fieldType;

    @Field("mandatory")
    private Boolean mandatory;

    @Field("variable_name")
    private String variableName;

    @Field("units")
    private String units;

    @Field("conditional")
    private Boolean conditional;

    @Field("creation_date")
    private Instant creationDate;

    @Field("edit_date")
    private Instant editDate;

    @Field("actions")
    private String actions;

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

    public Questions id(String id) {
        this.id = id;
        return this;
    }

    public String getQuestion() {
        return this.question;
    }

    public Questions question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public Questions subtitle(String subtitle) {
        this.subtitle = subtitle;
        return this;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getInfo() {
        return this.info;
    }

    public Questions info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public Questions fieldType(FieldType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Boolean getMandatory() {
        return this.mandatory;
    }

    public Questions mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getVariableName() {
        return this.variableName;
    }

    public Questions variableName(String variableName) {
        this.variableName = variableName;
        return this;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getUnits() {
        return this.units;
    }

    public Questions units(String units) {
        this.units = units;
        return this;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Boolean getConditional() {
        return this.conditional;
    }

    public Questions conditional(Boolean conditional) {
        this.conditional = conditional;
        return this;
    }

    public void setConditional(Boolean conditional) {
        this.conditional = conditional;
    }

    public Instant getCreationDate() {
        return this.creationDate;
    }

    public Questions creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getEditDate() {
        return this.editDate;
    }

    public Questions editDate(Instant editDate) {
        this.editDate = editDate;
        return this;
    }

    public void setEditDate(Instant editDate) {
        this.editDate = editDate;
    }

    public String getActions() {
        return this.actions;
    }

    public Questions actions(String actions) {
        this.actions = actions;
        return this;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Set<Answer> getAnswers() {
        return this.answers;
    }

    public Questions answers(Set<Answer> answers) {
        this.setAnswers(answers);
        return this;
    }

    public Questions addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setQuestions(this);
        return this;
    }

    public Questions removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setQuestions(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        if (this.answers != null) {
            this.answers.forEach(i -> i.setQuestions(null));
        }
        if (answers != null) {
            answers.forEach(i -> i.setQuestions(this));
        }
        this.answers = answers;
    }

    public Forms getForms() {
        return this.forms;
    }

    public Questions forms(Forms forms) {
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
        if (!(o instanceof Questions)) {
            return false;
        }
        return id != null && id.equals(((Questions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", subtitle='" + getSubtitle() + "'" +
            ", info='" + getInfo() + "'" +
            ", fieldType='" + getFieldType() + "'" +
            ", mandatory='" + getMandatory() + "'" +
            ", variableName='" + getVariableName() + "'" +
            ", units='" + getUnits() + "'" +
            ", conditional='" + getConditional() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", editDate='" + getEditDate() + "'" +
            ", actions='" + getActions() + "'" +
            "}";
    }
}
