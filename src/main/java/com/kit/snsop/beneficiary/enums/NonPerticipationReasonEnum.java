/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anwar
 */
public enum NonPerticipationReasonEnum {
    
    REASON_1(2,"All other eligible household members have other commitments that occupy their time"),
    REASON_2(3,"I am uncertain about the ability of the other household members to comply with the program's expectations or conditions"),
    REASON_3(4,"The health or physical condition of the other eligible household members prevents me from participating"),
    REASON_4(5,"I am not convinced that the program will provide meaningful benefits"),
    REASON_5(6,"The program activities don't align with my interests"),
    REASON_6(7,"All eligible household members have already been nominated"),
    REASON_OTHER(8,"Other");

    Integer value;
    String reason;
    
    NonPerticipationReasonEnum(Integer value, String reason){
        this.reason = reason;
        this.value = value;
    }
    
    static Map<String, NonPerticipationReasonEnum> valueMap = new HashMap<>();
    
    static{
        for(NonPerticipationReasonEnum e : NonPerticipationReasonEnum.values()){
            valueMap.put(e.reason, e);
        }
    }
    
     public static NonPerticipationReasonEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static NonPerticipationReasonEnum getNonPerticipationReason(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }
        for (NonPerticipationReasonEnum en : NonPerticipationReasonEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }

    public String getReason() {
        return reason;
    }
    
    public Integer getValue(){
        return value;
    }
}
