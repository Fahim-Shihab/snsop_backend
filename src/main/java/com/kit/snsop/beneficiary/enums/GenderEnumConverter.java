package com.kit.snsop.beneficiary.enums;

import jakarta.persistence.AttributeConverter;

public class GenderEnumConverter implements AttributeConverter<GenderEnum, String> {
    @Override
    public String convertToDatabaseColumn(GenderEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        if (dbData != null) {
            return GenderEnum.getEnumFromValue(dbData);
        }
        return null;
    }
}
