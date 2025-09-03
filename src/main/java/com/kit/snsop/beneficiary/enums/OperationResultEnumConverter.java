package com.kit.snsop.beneficiary.enums;

import jakarta.persistence.AttributeConverter;

public class OperationResultEnumConverter implements AttributeConverter<OperationResult, String> {
    @Override
    public String convertToDatabaseColumn(OperationResult attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public OperationResult convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return OperationResult.getEnumFromValue(dbData);
        }
        return null;
    }
}
