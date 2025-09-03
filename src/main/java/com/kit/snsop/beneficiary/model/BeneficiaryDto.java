package com.kit.snsop.beneficiary.model;

import com.kit.snsop.beneficiary.domain.*;
import com.kit.snsop.beneficiary.enums.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.usertype.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * @author anwar
 */
@Data
@ToString
@NoArgsConstructor
@Document(indexName = "index_beneficiary")
public class BeneficiaryDto implements Serializable {

    @Id
    @NotEmpty(message = "Application ID is missing")
    private String applicationId;

    @NotEmpty(message = "Respondent first name should not be null")
    @Size(min = 3, max = 100, message = "Respondent first name length should be between 3 and 100")
    private String respondentFirstName;

    @NotEmpty(message = "Respondent middle name should not be null")
    @Size(min = 3, max = 100, message = "Respondent middle name length should be between 3 and 100")
    private String respondentMiddleName;

    @NotEmpty(message = "Respondent last name should not be null")
    @Size(min = 3, max = 100, message = "Respondent last name length should be between 3 and 100")
    private String respondentLastName;

    private String respondentNickName;

    @Size(min = 3, max = 100, message = "Spouse first name length should be between 3 and 100")
    private String spouseFirstName;

    @Size(min = 3, max = 100, message = "Spouse middle name length should be between 3 and 100")
    private String spouseMiddleName;

    @Size(min = 3, max = 100, message = "Spouse last name length should be between 3 and 100")
    private String spouseLastName;

    private String spouseNickName;

    @NotNull(message = "Relationship should not be null")
    private RelationshipEnum relationshipWithHouseholdHead;

    private String relationshipOther;

    @NotNull
    //@Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer respondentAge;

    @NotNull(message = "Gender should not be null")
    private GenderEnum respondentGender;

    @NotNull(message = "Merital status should not be null")
    private MaritalStatusEnum respondentMaritalStatus;

    @NotNull(message = "Legal status should not be null")
    private LegalStatusEnum respondentLegalStatus;

    @NotNull(message = "Document Type should not be null")
    private DocumentTypeEnum documentType;

    @Size(min = 2, max = 50, message = "Other type document length must be between 2 and 50")
    private String documentTypeOther;

    //@NotEmpty(message = "Respondent ID should not be null")
    @Size(min = 2, max = 50, message = "ID length must be between 2 and 50")
    private String respondentId;

    //@NotEmpty(message = "Respondent phone number should not be null")
    @Size(max = 10, min = 10, message = "Phone number length should be 10")
    private String respondentPhoneNo;

    @NotNull(message = "Income source should not be null")
    private IncomeSourceEnum householdIncomeSource;

    private String incomeSourceOther;

    @NotNull(message = "Monthly average income should not be null")
    @PositiveOrZero(message = "Monthly average income should be positive value")
    private Integer householdMonthlyAvgIncome;

    @NotNull(message = "Currency should not be null")
    private CurrencyEnum currency;

    @NotNull(message = "Selection criteria should not be null")
    private SelectionCriteriaEnum selectionCriteria;

    @NotNull(message = "Selection reason should not be null")
    private List<SelectionReasonEnum> selectionReason;

    @NotNull(message = "Address should not be null")
    @Valid
    private AddressDto address;

    //@NotNull(message = "Location should not be null")
    @Valid
    private LocationDto location;

    @NotNull(message = "Household size should not be null")
    @Positive(message = "Household size should be positive value")
    private Integer householdSize;

    @Valid
    private HouseholdInfoDto householdMember2;
    @Valid
    private HouseholdInfoDto householdMember5;
    @Valid
    private HouseholdInfoDto householdMember17;
    @Valid
    private HouseholdInfoDto householdMember35;
    @Valid
    private HouseholdInfoDto householdMember64;
    @Valid
    private HouseholdInfoDto householdMember65;

    @NotNull(message = "Read/write infomration should not be null")
    private Boolean isReadWrite;

    @NotNull(message = "Read/write infomration should not be null")
    @PositiveOrZero(message = "Read/write size infomration should be positive value")
    private Integer memberReadWrite;

    @NotNull(message = "Other perticipation infomration should not be null")
    private Boolean isOtherMemberPerticipating;

    private NonPerticipationReasonEnum notPerticipationReason;

    private String notPerticipationOtherReason;

    private List<@Valid NomineeDto> nominees;

    @NotNull(message = "Beneficiary biometric should not be null")
    private @Valid BiometricDto biometric;

    //@NotNull(message = "Alternate payee1 must not be null")
    @Valid
    private AlternateDto alternatePayee1;
    //@NotNull(message = "Alternate payee2 must not be null")
    @Valid
    private AlternateDto alternatePayee2;

    private Long createdBy;
    private Long updatedBy;

    private Date created;
    private Date updated;

    @Transient
    private String requestId;

    public BeneficiaryDto(PrimaryBeneficiary primaryBeneficiary) {
        if (primaryBeneficiary != null && primaryBeneficiary.getBeneficiary() != null) {
            Beneficiary beneficiary = primaryBeneficiary.getBeneficiary();
            this.applicationId = beneficiary.getApplicationId();
            this.respondentFirstName = beneficiary.getRespondentFirstName();
            this.respondentMiddleName = beneficiary.getRespondentMiddleName();
            this.respondentLastName = beneficiary.getRespondentLastName();
            this.respondentNickName = beneficiary.getRespondentNickName();
            if(beneficiary.getBeneficiaryFamily() != null) {
                this.spouseFirstName = beneficiary.getBeneficiaryFamily().getSpouseFirstName();
                this.spouseMiddleName = beneficiary.getBeneficiaryFamily().getSpouseMiddleName();
                this.spouseLastName = beneficiary.getBeneficiaryFamily().getSpouseLastName();
                this.spouseNickName = beneficiary.getBeneficiaryFamily().getSpouseNickName();
                this.relationshipWithHouseholdHead = beneficiary.getBeneficiaryFamily().getRelationshipWithHouseholdHead();
                this.relationshipOther = beneficiary.getBeneficiaryFamily().getRelationshipOther();
                this.memberReadWrite = beneficiary.getBeneficiaryFamily().getMemberReadWrite();
                this.isOtherMemberPerticipating = beneficiary.getBeneficiaryFamily().getIsOtherMemberPerticipating();
                this.notPerticipationReason = beneficiary.getBeneficiaryFamily().getNotPerticipationReason();
                this.notPerticipationOtherReason = beneficiary.getBeneficiaryFamily().getNotPerticipationOtherReason();
                this.currency = beneficiary.getBeneficiaryFamily().getCurrency();
            }

            this.respondentAge = primaryBeneficiary.getRespondentAge();
            this.respondentGender = primaryBeneficiary.getRespondentGender();
            this.respondentMaritalStatus = beneficiary.getRespondentMaritalStatus();
            this.respondentLegalStatus = primaryBeneficiary.getRespondentLegalStatus();
            this.documentType = beneficiary.getDocumentType();
            this.documentTypeOther = beneficiary.getDocumentTypeOther();
            this.respondentId = primaryBeneficiary.getRespondentId();
            this.respondentPhoneNo = primaryBeneficiary.getRespondentPhoneNo();
            this.householdIncomeSource = primaryBeneficiary.getHouseholdIncomeSource();
            this.incomeSourceOther = primaryBeneficiary.getIncomeSourceOther();
            this.householdMonthlyAvgIncome = primaryBeneficiary.getHouseholdMonthlyAvgIncome();
            this.selectionCriteria = primaryBeneficiary.getSelectionCriteria();
            if (!StringUtils.isEmpty(primaryBeneficiary.getSelectionReasons())) {
                this.setSelectionReason(new ArrayList<>());
                String[] reasons = primaryBeneficiary.getSelectionReasons().split(",");
                for (String reason : reasons) {
                    this.getSelectionReason().add(SelectionReasonEnum.valueOf(reason));
                }
            }

            this.address = new AddressDto(beneficiary.getAddress());
            if(!StringUtils.isEmpty(primaryBeneficiary.getLatitude())
            && !StringUtils.isEmpty(primaryBeneficiary.getLongitude())){
                this.location = new LocationDto(Double.parseDouble(primaryBeneficiary.getLatitude()), Double.parseDouble(primaryBeneficiary.getLongitude()));
            }

            this.householdSize = primaryBeneficiary.getHouseholdSize();
            this.isReadWrite = beneficiary.getIsReadWrite();
            this.createdBy = primaryBeneficiary.getCreatedBy();
            if (beneficiary.getNominees() != null && beneficiary.getNominees().size() > 0) {
                this.nominees = new ArrayList<>();
                beneficiary.getNominees().forEach(n -> nominees.add(new NomineeDto(n)));
            }

            if (beneficiary.getBiometric() != null) {
                BiometricDto biometrics = setBiometricData(beneficiary.getBiometric(), BiometricUserType.BENEFICIARY);
                this.setBiometric(biometrics);
            }

            resolveAlternate(this, beneficiary.getAlternates());
            resolveHousehold(this, beneficiary.getHouseholdInfos());

            this.created = primaryBeneficiary.getCreated();
            this.updated = primaryBeneficiary.getUpdated();
        }
    }

    private void resolveAlternate(BeneficiaryDto beneficiaryDto, List<Alternate> alternates) {
        for (Alternate alternate : alternates) {
            if(alternate.getAltIndex() == 1){
                beneficiaryDto.setAlternatePayee1(new AlternateDto(alternate));
                BiometricDto biometrics = setBiometricData(alternate.getBiometric(), BiometricUserType.ALTERNATE);
                biometrics.setApplicationId(beneficiaryDto.getApplicationId());
                beneficiaryDto.getAlternatePayee1().setBiometric(biometrics);
            }else{
                beneficiaryDto.setAlternatePayee2(new AlternateDto(alternate));
                BiometricDto biometrics = setBiometricData(alternate.getBiometric(), BiometricUserType.ALTERNATE);
                biometrics.setApplicationId(beneficiaryDto.getApplicationId());
                beneficiaryDto.getAlternatePayee2().setBiometric(biometrics);
            }
        }
    }

    private void resolveHousehold(BeneficiaryDto beneficiaryDto, List<HouseholdInfo> households) {
        for(HouseholdInfo household : households) {
            if(household.getMinimumAge().equals(0) && household.getMaximumAge().equals(2)) {
                beneficiaryDto.setHouseholdMember2(new HouseholdInfoDto(household));
            }else if(household.getMinimumAge().equals(3) && household.getMaximumAge().equals(5)) {
                beneficiaryDto.setHouseholdMember5(new HouseholdInfoDto(household));
            }else if(household.getMinimumAge().equals(6) && household.getMaximumAge().equals(17)) {
                beneficiaryDto.setHouseholdMember17(new HouseholdInfoDto(household));
            }else if(household.getMinimumAge().equals(18) && household.getMaximumAge().equals(35)) {
                beneficiaryDto.setHouseholdMember35(new HouseholdInfoDto(household));
            }else if(household.getMinimumAge().equals(36) && household.getMaximumAge().equals(64)) {
                beneficiaryDto.setHouseholdMember64(new HouseholdInfoDto(household));
            }else{
                beneficiaryDto.setHouseholdMember65(new HouseholdInfoDto(household));
            }
        }
    }

    private BiometricDto setBiometricData(Biometric biometric, BiometricUserType userType) {
        BiometricDto biometricDto = new BiometricDto();
        biometricDto.setPhoto(biometric.getPhoto());
        biometricDto.setBiometricUserType(userType);
        biometricDto.setWsqLt(biometric.getWsqLt());
        biometricDto.setWsqLi(biometric.getWsqLi());
        biometricDto.setWsqLm(biometric.getWsqLm());
        biometricDto.setWsqLr(biometric.getWsqLr());
        biometricDto.setWsqLs(biometric.getWsqLs());
        biometricDto.setWsqRt(biometric.getWsqRt());
        biometricDto.setWsqRi(biometric.getWsqRi());
        biometricDto.setWsqRm(biometric.getWsqRm());
        biometricDto.setWsqRr(biometric.getWsqRr());
        biometricDto.setWsqRs(biometric.getWsqRs());

        biometricDto.setNoFingerPrint(biometric.isNoFingerPrint());
        biometricDto.setNoFingerprintReasonText(biometric.getNoFingerprintReasonText());

        return biometricDto;

    }

    @Data
    public static class BeneficiaryEditRequest {

        private String requestId;

        @NotNull(message = "Application id can not be null")
        private String applicationId;

        @Valid
        private List<BiometricDto> biometrics;

        @NotEmpty(message = "Respondent first name should not be null")
        @Size(min = 3, max = 100, message = "Respondent first name length should be between 3 and 100")
        private String respondentFirstName;

        @NotEmpty(message = "Respondent middle name should not be null")
        @Size(min = 3, max = 100, message = "Respondent middle name length should be between 3 and 100")
        private String respondentMiddleName;

        @NotEmpty(message = "Respondent last name should not be null")
        @Size(min = 3, max = 100, message = "Respondent last name length should be between 3 and 100")
        private String respondentLastName;

        private String respondentNickName;

        @Size(min = 3, max = 100, message = "Spouse first name length should be between 3 and 100")
        private String spouseFirstName;

        @Size(min = 3, max = 100, message = "Spouse middle name length should be between 3 and 100")
        private String spouseMiddleName;

        @Size(min = 3, max = 100, message = "Spouse last name length should be between 3 and 100")
        private String spouseLastName;

        private String spouseNickName;

        @NotNull(message = "Relationship should not be null")
        private RelationshipEnum relationshipWithHouseholdHead;

        private String relationshipOther;

        @NotNull
        @Max(value = 150, message = "Age should not be greater than 150")
        private Integer respondentAge;

        @NotNull(message = "Gender should not be null")
        private GenderEnum respondentGender;

        @NotNull(message = "Merital status should not be null")
        private MaritalStatusEnum respondentMaritalStatus;

        @NotNull(message = "Legal status should not be null")
        private LegalStatusEnum respondentLegalStatus;

        @NotNull(message = "Document Type should not be null")
        private DocumentTypeEnum documentType;

        @Size(min = 2, max = 50, message = "Other type document length must be between 2 and 50")
        private String documentTypeOther;

        @Size(min = 2, max = 50, message = "ID length must be between 2 and 50")
        private String respondentId;

        @Size(max = 10, min = 10, message = "Phone number length should be 10")
        private String respondentPhoneNo;

        @NotNull(message = "Income source should not be null")
        private IncomeSourceEnum householdIncomeSource;

        private String incomeSourceOther;

        @NotNull(message = "Monthly average income should not be null")
        @PositiveOrZero(message = "Monthly average income should be positive value")
        private Integer householdMonthlyAvgIncome;

        @NotNull(message = "Currency should not be null")
        private CurrencyEnum currency;

        @NotNull(message = "Selection criteria should not be null")
        private SelectionCriteriaEnum selectionCriteria;

        @NotNull(message = "Selection reason should not be null")
        private List<SelectionReasonEnum> selectionReason;

        @NotNull(message = "Address should not be null")
        @Valid
        private AddressDto address;

        @Valid
        private LocationDto location;

        @NotNull(message = "Household size should not be null")
        @Positive(message = "Household size should be positive value")
        private Integer householdSize;

        @Valid
        private HouseholdInfo householdMember2;
        @Valid
        private HouseholdInfo householdMember5;
        @Valid
        private HouseholdInfo householdMember17;
        @Valid
        private HouseholdInfo householdMember35;
        @Valid
        private HouseholdInfo householdMember64;
        @Valid
        private HouseholdInfo householdMember65;

        @NotNull(message = "Read/write infomration should not be null")
        private Boolean isReadWrite;

        @NotNull(message = "Read/write infomration should not be null")
        @PositiveOrZero(message = "Read/write size infomration should be positive value")
        private Integer memberReadWrite;

        @NotNull(message = "Other perticipation infomration should not be null")
        private Boolean isOtherMemberPerticipating;

        private NonPerticipationReasonEnum notPerticipationReason;

        private String notPerticipationOtherReason;

        private List<@Valid NomineeDto> nominees;

        @Valid
        private AlternateDto.AlternateEditRequest alternatePayee1;

        @Valid
        private AlternateDto.AlternateEditRequest alternatePayee2;

        private Long updatedBy;

        public BeneficiaryEditRequest() {
        }

    }
}
