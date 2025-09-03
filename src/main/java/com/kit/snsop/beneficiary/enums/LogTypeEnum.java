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

public enum LogTypeEnum {
    RECONCILIATION("RECONCILIATION"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    REGISTRATION("REGISTRATION")
    ;
    
    private String value;

    static Map<String, LogTypeEnum> valueMap = new HashMap<>();

    static{
        for(LogTypeEnum e : LogTypeEnum.values()){
            valueMap.put(e.value.toLowerCase(), e);
        }
    }
    
    LogTypeEnum(String value){
        this.value = value;
    }

    public static LogTypeEnum getEnumFromValue(String value){
        return valueMap.get(value.trim().toLowerCase());
    }

    public static LogTypeEnum getLogType(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (LogTypeEnum logTypeEnum : LogTypeEnum.values()) {
            if (logTypeEnum.getValue().equals(value)) {
                return logTypeEnum;
            }
        }

        return null;
    }
    
    public String getValue(){
        return this.value;
    }
}
