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
public enum DocumentTypeEnum {
    PASSPORT("Passport"),
    NATIONAL_ID("National Id"),
    OTHER("Other"),
    NONE("None");
    
    String value;

    DocumentTypeEnum(String value) {
        this.value = value;
    }
    
    static Map<String, DocumentTypeEnum> valueMap = new HashMap<>();
    
    static{
        for(DocumentTypeEnum e : DocumentTypeEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
     public static DocumentTypeEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static DocumentTypeEnum getDocType(Integer ordinal) {

        if (ordinal == null) {
            return null;
        }
        for (DocumentTypeEnum docType : DocumentTypeEnum.values()) {
            if (docType.ordinal() == ordinal) {
                return docType;
            }
        }
        return null;
    }
}
