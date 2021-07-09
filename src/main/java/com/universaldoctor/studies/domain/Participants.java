package com.universaldoctor.studies.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.universaldoctor.studies.domain.enumeration.Status;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Participants.
 */
@Document(collection = "participants")
public class Participants implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("email")
    private String email;

    @Field("associated_forms")
    private String associatedForms;

    @Field("forms_completed")
    private Integer formsCompleted;

    @Field("languaje")
    private String languaje;

    @Field("invitation_status")
    private Status invitationStatus;

    @Field("actions")
    private String actions;

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

    public Participants id(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return this.email;
    }

    public Participants email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAssociatedForms() {
        return this.associatedForms;
    }

    public Participants associatedForms(String associatedForms) {
        this.associatedForms = associatedForms;
        return this;
    }

    public void setAssociatedForms(String associatedForms) {
        this.associatedForms = associatedForms;
    }

    public Integer getFormsCompleted() {
        return this.formsCompleted;
    }

    public Participants formsCompleted(Integer formsCompleted) {
        this.formsCompleted = formsCompleted;
        return this;
    }

    public void setFormsCompleted(Integer formsCompleted) {
        this.formsCompleted = formsCompleted;
    }

    public String getLanguaje() {
        return this.languaje;
    }

    public Participants languaje(String languaje) {
        this.languaje = languaje;
        return this;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
    }

    public Status getInvitationStatus() {
        return this.invitationStatus;
    }

    public Participants invitationStatus(Status invitationStatus) {
        this.invitationStatus = invitationStatus;
        return this;
    }

    public void setInvitationStatus(Status invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    public String getActions() {
        return this.actions;
    }

    public Participants actions(String actions) {
        this.actions = actions;
        return this;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public Study getStudy() {
        return this.study;
    }

    public Participants study(Study study) {
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
        if (!(o instanceof Participants)) {
            return false;
        }
        return id != null && id.equals(((Participants) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participants{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", associatedForms='" + getAssociatedForms() + "'" +
            ", formsCompleted=" + getFormsCompleted() +
            ", languaje='" + getLanguaje() + "'" +
            ", invitationStatus='" + getInvitationStatus() + "'" +
            ", actions='" + getActions() + "'" +
            "}";
    }
}
