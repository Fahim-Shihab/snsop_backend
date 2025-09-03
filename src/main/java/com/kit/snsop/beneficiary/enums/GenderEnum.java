/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.enums;

import io.micrometer.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anwar
 */
public enum GenderEnum {
    MALE("M"),
    FEMALE("F");
    
    private String value;
    
    GenderEnum(String value){
        this.value = value;
    }
    
    static Map<String, GenderEnum> valueMap = new HashMap<>();
    
    static{
        for(GenderEnum e : GenderEnum.values()){
            valueMap.put(e.value.toLowerCase(), e);
        }
    }
    
    public static GenderEnum getEnumFromValue(String value){
        return valueMap.get(value.trim().toLowerCase());
    }

    public static GenderEnum getGender(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (GenderEnum genderEnum : GenderEnum.values()) {
            if (genderEnum.getValue().equals(value)) {
                return genderEnum;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
}
