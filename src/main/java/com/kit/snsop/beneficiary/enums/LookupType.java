package com.kit.snsop.beneficiary.enums;

public enum LookupType {
    STATE("geo_states"),
    COUNTY("geo_county"),
    PAYAM("geo_payam"),
    BOMA("geo_boma");

    private final String value;

    LookupType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
