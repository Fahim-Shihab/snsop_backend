/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.enums;

/**
 *
 * @author anwar
 */
public enum OperationEnum {
    ALL("All"),
    REGISTRATION("Registration"),
    UPLOADER("Uploader"),
    HOUSEHOLD_VIEW("Household View"),
    HOUSEHOLD_EDIT("Household Edit"),
    HOUSEHOLD_DELETE("Household Delete"),
    HOUSEHOLD_APPROVAL("Household Approval"),
    MATCH_LIST_VIEW("Match List View"),
    MATCH_LIST_EDIT("Match List Edit"),
    MATCH_LIST_DELETE("Match List Delete"),
    ADJUDICATION("Adjudication"),
    USER_VIEW("User View"),
    USER_EDIT("User Edit"),
    USER_DELETE("User Delete"),
    AUDIT_TRAIL_VIEW("Audit Trail View"),
    DEVICE_MANAGEMENT("Device Management"),
    REPORT_VIEW("Report View")
    ;
    
    private String value;
    
    OperationEnum(String value){
        this.value = value;
    }
}
