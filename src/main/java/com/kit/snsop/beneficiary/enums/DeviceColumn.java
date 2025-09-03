package com.kit.snsop.beneficiary.enums;

public enum DeviceColumn {

    ID("device.id","Number"),
    CREATED("device.created","date"),
    CREATED_BY("device.created_by","Number"),
    DEVICE_ID("device.device_id","Text"),
    IMEI("device.imei","Text"),
    STATUS("device.status","Number"),
    UPDATED("device.updated","datet"),
    VERSION("device.version","Number"),
    ADDRESS_ID("device.address_id","Number"),
    BOMA_ID("address.boma_id", "Number"),
    COUNTY_ID("address.county_id", "Number"),
    PAYAM_ID("address.payam_id", "Number"),
    STATE_ID("address.state_id", "Number"),
    ADDRESS_LINE("address.address_line", "Number"),
    LOCATION_ID("device.location_id","Number"),
    UPDATED_BY("device.updated_by","Number");

    private final String value;
    private final String dataType;

    DeviceColumn(String value, String dataType) {
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
