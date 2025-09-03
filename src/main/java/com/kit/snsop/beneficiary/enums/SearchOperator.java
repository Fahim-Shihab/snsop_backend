package com.kit.snsop.beneficiary.enums;

public enum SearchOperator {
    EQUAL("="),
    LIKE("LIKE"),
    LTE("<="),
    GTE(">=");

    private final String operator;
    SearchOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
