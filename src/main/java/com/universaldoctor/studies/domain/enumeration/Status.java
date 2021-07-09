package com.universaldoctor.studies.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    ACCEPTED("Accepted"),
    PENDING("Pending"),
    REJECTED("Rejected");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
