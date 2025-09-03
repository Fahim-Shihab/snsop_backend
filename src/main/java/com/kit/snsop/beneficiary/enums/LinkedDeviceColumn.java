package com.kit.snsop.beneficiary.enums;

public enum LinkedDeviceColumn {

    ID("id","Number"),
    USER_ID("user_id","Number"),
    USER_NAME("user_name","Text"),
    DEVICE_ID("device_id","Text"),
    CREATED("created","datetime2"),
    CREATED_BY("created_by","Number");

    private final String value;
    private final String dataType;

    LinkedDeviceColumn(String value, String dataType) {
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
