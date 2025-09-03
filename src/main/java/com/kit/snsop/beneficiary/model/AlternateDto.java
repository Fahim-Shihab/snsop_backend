/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;


import java.io.Serializable;
import java.util.List;

import com.kit.snsop.beneficiary.domain.Alternate;
import com.kit.snsop.beneficiary.enums.*;
import com.kit.snsop.common.util.Utils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 *
 * @author anwar
 */
@Data
public class AlternateDto implements Serializable{

    @NotNull(message = "Payee first name should not be null")
    @NotEmpty(message = "Payee first name should not be null")
    @Size(min = 3, max = 100, message = "Payee name length should be between 3 and 100")
    private String payeeFirstName;

    @NotNull(message = "Payee middle name should not be null")
    @NotEmpty(message = "Payee middle name should not be null")
    @Size(min = 3, max = 100, message = "Payee middle name length should be between 3 and 100")
    private String payeeMiddleName;

    @NotNull(message = "Payee last name should not be null")
    @NotEmpty(message = "Payee last name should not be null")
    @Size(min = 3, max = 100, message = "Payee last name length should be between 3 and 100")
    private String payeeLastName;

    private String payeeNickName;

    @NotNull(message = "Payee gender should not be null")
    private GenderEnum payeeGender;

    @NotNull
    //@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer payeeAge;

    @NotNull(message = "Alternate relationship should not be null")
    private RelationshipEnum relationshipWithHouseholdHead;

    private String relationshipOther;

    @NotNull(message = "Document Type should not be null")
    private DocumentTypeEnum documentType;

    @Size(min = 2, max = 100, message = "Other type document length must be between 2 and 100")
    private String documentTypeOther;

    //@NotEmpty(message = "Payee national ID should not be null")
    @Size(min = 2, max = 50, message = "ID length must be between 2 and 50")
    private String nationalId;

    //@NotEmpty(message = "Payee phone number should not be null")
    @Size(min = 10, max = 10, message = "Payee phone number length should be 10")
    private String payeePhoneNo;

    @NotNull(message = "Alternate biometric should not be null")
    @Valid
    BiometricDto biometric;

    public AlternateDto() {
    }

    public AlternateDto(Alternate alternate) {
        if (alternate != null) {
            this.payeeFirstName = alternate.getPayeeFirstName();

            this.payeeMiddleName = alternate.getPayeeMiddleName();

            this.payeeLastName = alternate.getPayeeLastName();

            this.payeeNickName = alternate.getPayeeNickName();

            this.payeeGender = alternate.getPayeeGender();

            this.payeeAge = alternate.getPayeeAge();

            this.relationshipWithHouseholdHead = alternate.getRelationshipWithHouseholdHead();

            this.relationshipOther = alternate.getRelationshipOther();

            this.documentType = alternate.getDocumentType();

            this.documentTypeOther = alternate.getDocumentTypeOther();

            this.nationalId = alternate.getNationalId();

            this.payeePhoneNo = alternate.getPayeePhoneNo();

        }
    }

    public AlternateDto(Object[] esObject, int start) {
        if (esObject != null) {
            if (Utils.indexValueExists(esObject, start)) {
                this.nationalId = (String) esObject[start];
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.payeeAge = Utils.getIntegerFromObject(esObject[start]);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                String gender = (String)esObject[start];
                this.payeeGender = GenderEnum.getGender(gender);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.payeeFirstName = (String) esObject[start];
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.payeePhoneNo = (String) esObject[start];
            }
            start++;
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.payeeMiddleName = (String) esObject[start];
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.payeeNickName = (String) esObject[start];
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.payeeLastName = (String) esObject[start];
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.documentTypeOther = (String) esObject[start];
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                Integer docType = Utils.getIntegerFromObject(esObject[start]);
                this.documentType = DocumentTypeEnum.getDocType(docType);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                Integer relType = Utils.getIntegerFromObject(esObject[start]);
                this.relationshipWithHouseholdHead = RelationshipEnum.getRelationShip(relType);
            }
            start++;
            if (Utils.indexValueExists(esObject, start)) {
                this.relationshipOther = (String) esObject[start];
            }
        }
    }
    
    @Data
    public static class AlternateEditRequest {

        @NotNull(message = "ID can not be null")
        private Long id;

        @Valid
        private BiometricDto biometric;

        @NotNull(message = "Payee first name should not be null")
        @NotEmpty(message = "Payee first name should not be null")
        @Size(min = 3, max = 100, message = "Payee name length should be between 3 and 100")
        private String payeeFirstName;

        @NotNull(message = "Payee middle name should not be null")
        @NotEmpty(message = "Payee middle name should not be null")
        @Size(min = 3, max = 100, message = "Payee middle name length should be between 3 and 100")
        private String payeeMiddleName;

        @NotNull(message = "Payee last name should not be null")
        @NotEmpty(message = "Payee last name should not be null")
        @Size(min = 3, max = 100, message = "Payee last name length should be between 3 and 100")
        private String payeeLastName;

        private String payeeNickName;

        @NotNull(message = "Payee gender should not be null")
        private GenderEnum payeeGender;

        @NotNull
        @Min(value = 18, message = "Age should not be less than 18")
        @Max(value = 150, message = "Age should not be greater than 150")
        private Integer payeeAge;

        @NotNull(message = "Alternate relationship should not be null")
        private RelationshipEnum relationshipWithHouseholdHead;

        private String relationshipOther;

        @NotNull(message = "Document Type should not be null")
        private DocumentTypeEnum documentType;

        @Size(min = 2, max = 100, message = "Other type document length must be between 2 and 100")
        private String documentTypeOther;

        
        @Size(min = 2, max = 50, message = "ID length must be between 2 and 50")
        private String nationalId;

        
        @Size(min = 10, max = 10, message = "Payee phone number length should be 10")
        private String payeePhoneNo;
    }
}
