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
public enum OperationResult {
    FAILED("FAILED"),
    SUCCESS("SUCCESS"),
    PENDING("PENDING");

    private String value;

    OperationResult(String value){
        this.value = value;
    }

    static Map<String, OperationResult> valueMap = new HashMap<>();

    static{
        for(OperationResult e : OperationResult.values()){
            valueMap.put(e.value.toLowerCase(), e);
        }
    }

    public static OperationResult getEnumFromValue(String value){
        return valueMap.get(value.trim().toLowerCase());
    }

    public static OperationResult getOperationResult(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (OperationResult opEnum : OperationResult.values()) {
            if (opEnum.getValue().equals(value)) {
                return opEnum;
            }
        }

        return null;
    }

    public String getValue() {
        return value;
    }
}
