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
public enum LegalStatusEnum {
    HOST("Host"),
    RETURNEE("Returnee"),
    REFUGEE("Refugee"),
    IDP("Idp");
    
    private String value;
    
    LegalStatusEnum(String value){
        this.value = value;
    }
    
    static Map<String, LegalStatusEnum> valueMap = new HashMap<>();
    
    static{
        for(LegalStatusEnum e : LegalStatusEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
     public static LegalStatusEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static LegalStatusEnum getLegalStatus(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }

        for (LegalStatusEnum en : LegalStatusEnum.values()) {
            if (en.ordinal() == ordinal) {
                return en;
            }
        }
        return null;
    }
}
