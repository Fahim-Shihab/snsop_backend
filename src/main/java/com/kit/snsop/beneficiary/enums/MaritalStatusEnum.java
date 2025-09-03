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
public enum MaritalStatusEnum {
    SINGLE("Single"),
    MARRIED("Married"),
    WIDOW("Widow"),
    SEPARATED("Separated"),
    DIVORCE("Divorce");
    
    private String value;
    
    MaritalStatusEnum(String value){
        this.value = value;
    }
    
    static Map<String, MaritalStatusEnum> valueMap = new HashMap<>();
    
    static{
        for(MaritalStatusEnum e : MaritalStatusEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
     public static MaritalStatusEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static MaritalStatusEnum getMaritalStatus(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }
        for (MaritalStatusEnum en : MaritalStatusEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }
}
