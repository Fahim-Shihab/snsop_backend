/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anwar
 */
public enum SelectionReasonEnum {
    LIPW_REASON_1("Poor household with no sufficient income to sustain the household"),
    LIPW_REASON_2("Household contain able bodied youth member (18-35)"),
    LIPW_REASON_3("Household headed by young men and women between the ages of 18 and 35"),
    LIPW_REASON_4("Many members who are dependents (HH with dependants greater than 3)"),
    LIPW_REASON_5("Poor household which have persons with severe disabilities"),
    DIS_REASON_1("Child headed households with no alternate income support"),
    DIS_REASON_2("Elderly headed household lacking alternate income support and able bodied member"),
    DIS_REASON_3("Persons with disability headed household lacking alternate income support and able bodied member"),
    DIS_REASON_4("Chronically ill headed household lacking alternate income and able bodied member"),
    DIS_REASON_5("Female headed household lacking alternate income support and able-bodied member");
    
    String value;
    
    SelectionReasonEnum(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
    
    static Map<String, SelectionReasonEnum> valueMap = new HashMap<>();
    
    static{
        for(SelectionReasonEnum e : SelectionReasonEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
     public static SelectionReasonEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static SelectionReasonEnum getByName(String name) {
        if (!StringUtils.isEmpty(name)) {
            for (SelectionReasonEnum val : SelectionReasonEnum.values()) {
                if (val.name().equalsIgnoreCase(name)) {
                    return val;
                }
            }
        }
        return null;
    }
}
