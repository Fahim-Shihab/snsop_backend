/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;


import java.io.Serializable;

import com.kit.snsop.beneficiary.domain.Nominee;
import com.kit.snsop.beneficiary.enums.GenderEnum;
import com.kit.snsop.beneficiary.enums.NomineePopupResponse;
import com.kit.snsop.beneficiary.enums.OccupationEnum;
import com.kit.snsop.beneficiary.enums.RelationshipEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author anwar
 */
@Data
@ToString
public class NomineeDto implements Serializable {
    
    private Long id;
    
    @NotEmpty
    private String applicationId;

    @NotEmpty(message = "Nominee first name should not be null")
    @Size(min = 3, max = 100, message = "Nominee first name length should be between 3 and 100")
    private String nomineeFirstName;

    @NotEmpty(message = "Nominee middle name should not be null")
    @Size(min = 3, max = 100, message = "Nominee middle name length should be between 3 and 100")
    private String nomineeMiddleName;

    @NotEmpty(message = "Nominee last name should not be null")
    @Size(min = 3, max = 100, message = "Nominee last name length should be between 3 and 100")
    private String nomineeLastName;

    private String nomineeNickName;

    @NotNull(message = "Nominee relationship should not be null")
    private RelationshipEnum relationshipWithHouseholdHead;
    
    private String relationshipOther;

    @NotNull
    //@Min(value = 18, message = "Nominee age should not be less than 18")
    @Max(value = 150, message = "Nominee age should not be greater than 150")
    private Integer nomineeAge;
    
    private NomineePopupResponse nomineePopupResponse;

    @NotNull(message = "Nominee gender should not be null")
    private GenderEnum nomineeGender;

    @NotNull(message = "Read/write infomration should not be null")
    private Boolean isReadWrite;
    
    @NotNull(message = "Occupation should not be null")
    private OccupationEnum nomineeOccupation;
    
    @Size(min = 2, max = 100, message = "Other occupation length must be between 2 and 100")
    private String otherOccupation;

    public NomineeDto() {
    }

    public NomineeDto(Nominee nominee) {
        if (nominee != null) {
            this.id = nominee.getId();
            this.applicationId = nominee.getBeneficiary().getApplicationId();
            this.nomineeFirstName = nominee.getNomineeFirstName();
            this.nomineeMiddleName = nominee.getNomineeMiddleName();
            this.nomineeLastName = nominee.getNomineeLastName();
            this.nomineeNickName = nominee.getNomineeNickName();
            this.relationshipWithHouseholdHead = nominee.getRelationshipWithHouseholdHead();
            this.relationshipOther = nominee.getRelationshipOther();
            this.nomineeAge = nominee.getNomineeAge();
            this.nomineeGender = nominee.getNomineeGender();
            this.isReadWrite = nominee.getIsReadWrite();
            this.nomineeOccupation = nominee.getNomineeOccupation();
            this.otherOccupation = nominee.getOtherOccupation();
        }
    }
    
}
