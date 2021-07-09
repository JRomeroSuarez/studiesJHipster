package com.universaldoctor.studies.domain.enumeration;

/**
 * The FieldType enumeration.
 */
public enum FieldType {
    INFO("Info"),
    FREE_TEXT("Text"),
    RANGE("Range"),
    DATA("Data"),
    SINGLE_SELECT("Single"),
    NUMERIC("Numeric"),
    MULTIPLE_SELECT("Multiple");

    private final String value;

    FieldType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
