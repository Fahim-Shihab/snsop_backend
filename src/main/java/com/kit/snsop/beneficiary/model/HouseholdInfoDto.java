/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;

import com.kit.snsop.beneficiary.domain.HouseholdInfo;
import com.kit.snsop.common.util.Utils;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author anwar
 */
@Data
public class HouseholdInfoDto implements Serializable{
    
    @NotEmpty
    private String applicationId;
    
    @PositiveOrZero
    private Integer maleTotal;
    
    @PositiveOrZero
    private Integer maleBoth;
    
    @PositiveOrZero
    private Integer maleDisable;
    
    @PositiveOrZero
    private Integer maleChronicalIll;
    
    @PositiveOrZero
    private Integer femaleTotal;
    
    @PositiveOrZero
    private Integer femaleBoth;
    
    @PositiveOrZero
    private Integer femaleDisable;
    
    @PositiveOrZero
    private Integer femaleChronicalIll;

    public HouseholdInfoDto() {
    }

    public HouseholdInfoDto(HouseholdInfo householdInfo) {
        if (householdInfo != null) {
            this.applicationId = householdInfo.getBeneficiary().getApplicationId();
            this.maleTotal = householdInfo.getMaleTotal();
            this.maleBoth = householdInfo.getMaleBoth();
            this.maleDisable = householdInfo.getMaleDisable();
            this.maleChronicalIll = householdInfo.getMaleChronicalIll();
            this.femaleTotal = householdInfo.getFemaleTotal();
            this.femaleBoth = householdInfo.getFemaleBoth();
            this.femaleDisable = householdInfo.getFemaleDisable();
            this.femaleChronicalIll =  householdInfo.getFemaleChronicalIll();
        }
    }

    public HouseholdInfoDto(Object[] esObject, int start) {
        if (esObject != null) {
            if (Utils.indexValueExists(esObject, start)) {
                this.femaleChronicalIll = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.femaleDisable = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.femaleTotal = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.maleChronicalIll = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.maleDisable = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.maleTotal = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.maleTotal = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.femaleBoth = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.maleBoth = Utils.getIntegerFromObject(esObject[start]);
            }
        }
    }
}
