package com.kit.snsop.beneficiary.enums;

public enum RoleColumn {

    ID("id","Number"),
    CREATED("created","datetime2"),
    CREATED_BY("created_by","Number"),
    ROLE_NAME("role_name","Text"),
    ROLE_DESCRIPTION("role_description","Text");

    private final String value;
    private final String dataType;

    RoleColumn(String value, String dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public String getDataType() {
        return dataType;
    }
}
