/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author anwar
 */
public enum SelectionCriteriaEnum {
    LIPW("Component 1 (Public Works)"),
    DIS("Component 1 (Direct Income Support)"),
    ALL("All");
    
    String value;
    
    static Map<String, SelectionCriteriaEnum> valueMap = new HashMap<>();
    
    static{
        for(SelectionCriteriaEnum e : SelectionCriteriaEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
    public static SelectionCriteriaEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }
    
    SelectionCriteriaEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SelectionCriteriaEnum getSelectionCriteria(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }
        return Arrays.stream(SelectionCriteriaEnum.values()).filter(val-> val.ordinal() == ordinal).findFirst().get();
    }
}
