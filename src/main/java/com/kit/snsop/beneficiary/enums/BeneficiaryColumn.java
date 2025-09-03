package com.kit.snsop.beneficiary.enums;

public enum BeneficiaryColumn {

    ID("beneficiary.id","Number"),
    AFIS_STATUS("beneficiary.afis_status","Number"),
    APPLICATION_ID("beneficiary.application_id","Text"),
    CREATED("beneficiary.created","Date"),
    CREATED_BY("beneficiary.created_by","Number"),
    CURRENCY("beneficiary.currency","Number"),
    HOUSEHOLD_INCOME_SOURCE("beneficiary.household_income_source","Number"),
    HOUSEHOLD_MONTHLY_AVG_INCOME("beneficiary.household_monthly_avg_income","Number"),
    HOUSEHOLD_SIZE("beneficiary.household_size","Number"),
    IS_OTHER_MEMBER_PERTICIPATING("beneficiary.is_other_member_perticipating","bool"),
    IS_READ_WRITE("beneficiary.is_read_write","bool"),
    MEMBER_READ_WRITE("beneficiary.member_read_write","Number"),
    NON_PERTICIPATION_OTHER_REASON("beneficiary.non_perticipation_other_reason","Text"),
    NON_PERTICIPATION_REASON("beneficiary.non_perticipation_reason","Number"),
    RELATIONSHIP_WITH_HOUSEHOLD("beneficiary.relationship_with_household","Number"),
    RESPONDENT_AGE("beneficiary.respondent_age","Number"),
    RESPONDENT_FIRST_NAME("beneficiary.respondent_first_name","Text"),
    RESPONDENT_GENDER("beneficiary.respondent_gender","Number"),
    RESPONDENT_ID("beneficiary.respondent_id","Text"),
    RESPONDENT_LAST_NAME("beneficiary.respondent_last_name","Text"),
    RESPONDENT_LEGAL_STATUS("beneficiary.respondent_legal_status","Number"),
    RESPONDENT_MARITAL_STATUS("beneficiary.respondent_marital_status","Number"),
    RESPONDENT_MIDDLE_NAME("beneficiary.respondent_middle_name","Text"),
    RESPONDENT_PHONE_NO("beneficiary.respondent_phone_no","Text"),
    SELECTION_CRITERIA("beneficiary.selection_criteria","Number"),
    SELECTION_REASON("beneficiary.selection_reason","Number"),
    SPOUSE_FIRST_NAME("beneficiary.spouse_first_name","Text"),
    SPOUSE_LAST_NAME("beneficiary.spouse_last_name","Text"),
    SPOUSE_MIDDLE_NAME("beneficiary.spouse_middle_name","Text"),
    STATUS("beneficiary.status","Number"),
    UPDATED("beneficiary.updated","Date"),
    VERSION("beneficiary.version","Number"),
    ADDRESS_ID("address_id","Number"),
    BOMA_ID("address.boma_id", "Number"),
    COUNTY_ID("address.county_id", "Number"),
    PAYAM_ID("address.payam_id", "Number"),
    STATE_ID("address.state_id", "Number"),
    ADDRESS_LINE("address.address_line", "Number");

    private final String value;
    private final String dataType;

    BeneficiaryColumn(String value, String dataType) {
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
