package com.kit.snsop.beneficiary.enums;

public enum IdmUserColumn {

    ID("idm_user.id", "Number"),
    DATE_OF_BIRTH("idm_user.date_of_birth", "Date"),
    EMAIL("idm_user.email", "Text"),
    FIRST_NAME("idm_user.first_name", "Text"),
    LAST_NAME("idm_user.last_name", "Text"),
    MIDDLE_NAME("idm_user.middle_name", "Text"),
    PASSWORD("idm_user.password", "Text"),
    PHONE("idm_user.phone", "Text"),
    GENDER("idm_user.gender", "Number"),
    STATUS("idm_user.status", "Number"),
    USER_NAME("idm_user.user_name", "Text"),
    ADDRESS_ID("idm_user.address_id", "Number"),
    BOMA_ID("address.boma_id", "Number"),
    COUNTY_ID("address.county_id", "Number"),
    PAYAM_ID("address.payam_id", "Number"),
    STATE_ID("address.state_id", "Number"),
    ADDRESS_LINE("address.address_line", "Number");

    private final String value;
    private final String dataType;

    IdmUserColumn (String value, String dataType) {
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
