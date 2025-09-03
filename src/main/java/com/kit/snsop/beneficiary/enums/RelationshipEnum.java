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
public enum RelationshipEnum {
    HOUSEHOLD_HEAD("Head (Household head)"),
    SPOUSE("Spouse (Wife/Husband)"),
    SON_DAUGHTER("Son/Daughter"),
    SON_DAUGHTER_IN_LAW("Son-in-law/Daughter-in-law"),
    GRANDCHILD("Grandchild"),
    PARENT("Parent (Father/Mother)"),
    PARENT_IN_LAW("Parent-in-law (Father-in-law/Mother-in-law)"),
    SIBLING("Brother/Sister"),
    SIBLING_IN_LAW("Brother-in-law/Sister-in-law"),
    OTHER("Other relative"),
    DOMESTIC_WORKER("Domestic Worker"),
    NO_RELATION("Non-relative"),
    UNKNOWN("Unknown");
    
    private String value;
    
    static Map<String, RelationshipEnum> valueMap = new HashMap<>();
    
    static{
        for(RelationshipEnum e : RelationshipEnum.values()){
            valueMap.put(e.value, e);
        }
    }
    
    RelationshipEnum(String value){
        this.value = value;
    }
    
    public static RelationshipEnum getEnumFromValue(String value){
        return valueMap.get(value.trim());
    }

    public static RelationshipEnum getRelationShip(Integer ordinal) {
        if (ordinal == null) {
            return null;
        }
        for (RelationshipEnum rel : RelationshipEnum.values()) {
            if (rel.ordinal() == ordinal) {
                return rel;
            }
        }
        return null;
    }
}
