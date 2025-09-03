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
public enum IncomeSourceEnum {
    NONE("None"),
    SELLING_OF_FARM("Selling of farm"),
    SELLING_OF_NON_FARM("Selling of non farm"),
    CAUSAL_LABOUR("Causal labour"),
    FORMAL_EMPLOYMENT("Formal employment"),
    REMITTANCES("Remittances"),
    GIFT("Gift"),
    FROM_GOVT("From govt"),
    FROM_NGO("From ngo"),
    PENSION("Pension"),
    OTHER("Other");
    
    private String value;
    
    IncomeSourceEnum(String value){
        this.value = value;
    }
    
    static Map<String, IncomeSourceEnum> valueMap = new HashMap<>();
    
    static{
        for(IncomeSourceEnum e : IncomeSourceEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
     public static IncomeSourceEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static IncomeSourceEnum getIncomeSource(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }
        for (IncomeSourceEnum en : IncomeSourceEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }
}
