package com.kit.snsop.beneficiary.enums;

import jakarta.persistence.AttributeConverter;

public class LogTypeEnumConverter implements AttributeConverter<LogTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(LogTypeEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public LogTypeEnum convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return LogTypeEnum.getEnumFromValue(dbData);
        }
        return null;
    }
}
