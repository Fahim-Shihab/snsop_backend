package com.kit.snsop.common.payload;

import java.util.Arrays;

public enum ErrorCode {
    GENERAL_ERROR,
    REQUIRED_PARAMETER_MISSING,
    REQUIRED_PARAMETER_INVALID,
    DUPLICATE_ENTRY,
    AUTHENTICATION_ERROR,
    SERVICE_ERROR,
    UNSPECIFIED_ERROR,
    SERVICE_NOT_IMPLEMENTED,
    DATA_NOT_FOUND,
    AUTHORIZATION_ERROR;

    public static ErrorCode getValue(int index) {
        return Arrays.stream(values())
                .filter(e -> e.ordinal() == index)
                .findFirst()
                .orElse(GENERAL_ERROR);
    }
}
