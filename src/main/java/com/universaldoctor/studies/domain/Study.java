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
 * A Study.
 */
@Document(collection = "study")
public class Study implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("num_participants")
    private Integer numParticipants;

    @Field("num_forms")
    private Integer numForms;

    @DBRef
    @Field("forms")
    @JsonIgnoreProperties(value = { "questions", "formAnswers", "study" }, allowSetters = true)
    private Set<Forms> forms = new HashSet<>();

    @DBRef
    @Field("participants")
    @JsonIgnoreProperties(value = { "study" }, allowSetters = true)
    private Set<Participants> participants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Study id(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Study title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Study description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumParticipants() {
        return this.numParticipants;
    }

    public Study numParticipants(Integer numParticipants) {
        this.numParticipants = numParticipants;
        return this;
    }

    public void setNumParticipants(Integer numParticipants) {
        this.numParticipants = numParticipants;
    }

    public Integer getNumForms() {
        return this.numForms;
    }

    public Study numForms(Integer numForms) {
        this.numForms = numForms;
        return this;
    }

    public void setNumForms(Integer numForms) {
        this.numForms = numForms;
    }

    public Set<Forms> getForms() {
        return this.forms;
    }

    public Study forms(Set<Forms> forms) {
        this.setForms(forms);
        return this;
    }

    public Study addForms(Forms forms) {
        this.forms.add(forms);
        forms.setStudy(this);
        return this;
    }

    public Study removeForms(Forms forms) {
        this.forms.remove(forms);
        forms.setStudy(null);
        return this;
    }

    public void setForms(Set<Forms> forms) {
        if (this.forms != null) {
            this.forms.forEach(i -> i.setStudy(null));
        }
        if (forms != null) {
            forms.forEach(i -> i.setStudy(this));
        }
        this.forms = forms;
    }

    public Set<Participants> getParticipants() {
        return this.participants;
    }

    public Study participants(Set<Participants> participants) {
        this.setParticipants(participants);
        return this;
    }

    public Study addParticipants(Participants participants) {
        this.participants.add(participants);
        participants.setStudy(this);
        return this;
    }

    public Study removeParticipants(Participants participants) {
        this.participants.remove(participants);
        participants.setStudy(null);
        return this;
    }

    public void setParticipants(Set<Participants> participants) {
        if (this.participants != null) {
            this.participants.forEach(i -> i.setStudy(null));
        }
        if (participants != null) {
            participants.forEach(i -> i.setStudy(this));
        }
        this.participants = participants;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Study)) {
            return false;
        }
        return id != null && id.equals(((Study) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Study{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", numParticipants=" + getNumParticipants() +
            ", numForms=" + getNumForms() +
            "}";
    }
}
