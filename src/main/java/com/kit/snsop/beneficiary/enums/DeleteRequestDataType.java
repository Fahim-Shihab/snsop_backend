package com.kit.snsop.beneficiary.enums;

public enum DeleteRequestDataType {
    BENEFICIARY("BENEFICIARY"),
    PAYROLL("PAYROLL"),
    FINGER_TEMPLATE("FINGER_TEMPLATE"),
    SUBJECTS("SUBJECTS");

    private final String value;

    DeleteRequestDataType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
