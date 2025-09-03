package com.kit.snsop.beneficiary.domain;

import java.util.ArrayList;
import java.util.List;
import com.kit.snsop.beneficiary.enums.*;
import com.kit.snsop.beneficiary.model.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@Entity
@Table(name = "P2_BENEFICIARY")
@Data
@NoArgsConstructor
public class Beneficiary extends BaseEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "household_number", nullable = false, length = 100)
    private String applicationId;

    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String respondentFirstName;

    @Column(name = "MIDDLE_NAME", nullable = false, length = 100)
    private String respondentMiddleName;

    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String respondentLastName;

    @Column(name = "NICK_NAME", length = 100)
    private String respondentNickName;

    @Column(name = "FULL_NAME", length = 100, insertable = false, updatable = false)
    private String respondentFullName;

    @Column(name = "AGE", nullable = false)
    private Integer respondentAge;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GenderEnum respondentGender;

    @Column(name = "MARITAL_STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private MaritalStatusEnum respondentMaritalStatus;

    @Column(name = "LEGAL_STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private LegalStatusEnum respondentLegalStatus;
    
    @Column(name = "DOCUMENT_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DocumentTypeEnum documentType;
    
    @Column(name = "DOC_TYPE_OTHER", length = 100)
    private String documentTypeOther;

    @Column(name = "DOCUMENT_ID", length = 50)
    private String respondentId;

    @Column(name = "PHONE_NO", length = 10)
    private String respondentPhoneNo;

    @Column(name = "SELECTION_CRITERIA", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SelectionCriteriaEnum selectionCriteria;
    
    @Column(name = "SELECTION_REASONS", nullable = false)
    private String selectionReasons;

    @Column(name = "CAN_READ_WRITE", nullable = false)
    private Boolean isReadWrite;

    @Column(name = "HAS_MOBILE_WALLET")
    private Integer hasMobileWallet;

    @Column(name = "MOBILE_WALLET_ID")
    private Integer mobileWalletId;

    @Column(name = "MOBILE_WALLET_NUMBER")
    private String mobileWalletNumber;

    @Column(name = "REGISTRATION_PHASE", length = 10)
    private String registrationPhase;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<HouseholdInfo> householdInfos;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Alternate> alternates;

    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Nominee> nominees;

    @OneToOne(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    private Biometric biometric;

    @OneToOne(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    private BeneficiaryFamily beneficiaryFamily;
    
    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;
    
    @Column(name = "AFIS_STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private AfisStatusEnum afisStatus;

    @Column(name = "ES_SYNC_STATUS")
    private Integer esSyncStatus;

    public Beneficiary(BeneficiaryDto beneficiaryDto) {
        customFieldSet(this, beneficiaryDto);
    }

    private void customFieldSet(Beneficiary beneficiary, BeneficiaryDto beneficiaryDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.map(beneficiaryDto, this);

        if (beneficiaryDto.getBiometric() != null) {
            Biometric bio = setBiometricData(beneficiaryDto.getBiometric());
            bio.setBeneficiary(beneficiary);
            beneficiary.setBiometric(bio);
        }
        if (beneficiaryDto.getNominees() != null && !beneficiaryDto.getNominees().isEmpty()) {
            beneficiary.setNominees(new ArrayList<>());
            int nomineeIndex = 1;
            for (NomineeDto nDto : beneficiaryDto.getNominees()) {
                
                try{
                    NomineePopupResponse nomineePopupResponse = NomineePopupResponse.decode(nDto.getNomineeAge());
                    Integer nomineeAge = nDto.getNomineeAge() % 1000;
                    nDto.setNomineeAge(nomineeAge);
                    nDto.setNomineePopupResponse(nomineePopupResponse);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                
                Nominee nominee = mapper.map(nDto, Nominee.class);
                nominee.setId(null);
                nominee.setNomineeIndex(nomineeIndex++);
                nominee.setBeneficiary(beneficiary);
                beneficiary.getNominees().add(nominee);
            }
        }

        List<Alternate> alternates = new ArrayList<>();

        if(beneficiaryDto.getAlternatePayee1() != null && beneficiaryDto.getAlternatePayee1().getBiometric() != null){
            Alternate alternate = new Alternate();
            mapper.map(beneficiaryDto.getAlternatePayee1(), alternate);
            Biometric bio = setBiometricData(beneficiaryDto.getAlternatePayee1().getBiometric());
            bio.setAlternate(alternate);
            alternate.setAltIndex(1);
            alternate.setBeneficiary(beneficiary);
            alternates.add(alternate);
        }
        
        if(beneficiaryDto.getAlternatePayee2() != null && beneficiaryDto.getAlternatePayee2().getBiometric() != null){
            Alternate alternate = new Alternate();
            mapper.map(beneficiaryDto.getAlternatePayee2(), alternate);
            Biometric bio = setBiometricData(beneficiaryDto.getAlternatePayee2().getBiometric());
            bio.setAlternate(alternate);
            alternate.setAltIndex(2);
            alternate.setBeneficiary(beneficiary);
            alternates.add(alternate);
        }

        beneficiary.setAlternates(alternates);

        List<HouseholdInfo> householdInfos = new ArrayList<>();
        if(beneficiaryDto.getHouseholdMember2() != null){
            householdInfos.add(getHouseholdInfoByDto(beneficiary, beneficiaryDto.getHouseholdMember2(), 0, 2));
        }
        if(beneficiaryDto.getHouseholdMember5() != null){
            householdInfos.add(getHouseholdInfoByDto(beneficiary, beneficiaryDto.getHouseholdMember5(), 3, 5));
        }
        if(beneficiaryDto.getHouseholdMember17() != null){
            householdInfos.add(getHouseholdInfoByDto(beneficiary, beneficiaryDto.getHouseholdMember17(), 6, 17));
        }
        if(beneficiaryDto.getHouseholdMember35() != null){
            householdInfos.add(getHouseholdInfoByDto(beneficiary, beneficiaryDto.getHouseholdMember35(), 18, 35));
        }
        if(beneficiaryDto.getHouseholdMember64() != null){
            householdInfos.add(getHouseholdInfoByDto(beneficiary, beneficiaryDto.getHouseholdMember64(), 36, 64));
        }
        if(beneficiaryDto.getHouseholdMember65() != null){
            householdInfos.add(getHouseholdInfoByDto(beneficiary, beneficiaryDto.getHouseholdMember65(), 65, null));
        }

        beneficiary.getAddress().setBeneficiary(beneficiary);
        if(beneficiaryDto.getLocation() != null){
            beneficiary.getAddress().setLat(beneficiaryDto.getLocation().getLat());
            beneficiary.getAddress().setLon(beneficiaryDto.getLocation().getLon());
        }

        if(beneficiaryDto.getSelectionReason() != null){
            StringBuilder sb = new StringBuilder();
            for(SelectionReasonEnum selectionEnum : beneficiaryDto.getSelectionReason()){
                if(selectionEnum != null){
                    sb.append(selectionEnum.name());
                    sb.append(",");
                }
            }
            beneficiary.setSelectionReasons(sb.toString());
        }
    }


    private HouseholdInfo getHouseholdInfoByDto(Beneficiary beneficiary, HouseholdInfoDto householdInfoDto, Integer minAge, Integer maxAge) {
        HouseholdInfo householdInfo = new HouseholdInfo();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.map(householdInfoDto, householdInfo);
        householdInfo.setBeneficiary(beneficiary);
        householdInfo.setMinimumAge(minAge);
        householdInfo.setMaximumAge(maxAge);
        return householdInfo;
    }

    private Biometric setBiometricData(BiometricDto bDto) {
        Biometric bio = new Biometric();
        
        boolean noFingerprint = false;
        NoFingerprintReason reason = null;
        String reasonTxt = null;

        if(!noFingerprint && bDto.isNoFingerPrint()){
            noFingerprint = true;
        }

        if(reason == null && bDto.getNoFingerprintReason() != null){
            reason = bDto.getNoFingerprintReason();
        }

        if(reasonTxt == null && !StringUtils.isEmpty(bDto.getNoFingerprintReasonText())){
            reasonTxt = bDto.getNoFingerprintReasonText();
        }
        bio.setPhoto(bDto.getPhoto());
        bio.setWsqLt(bDto.getWsqLt());
        bio.setWsqLi(bDto.getWsqLi());
        bio.setWsqLm(bDto.getWsqLm());
        bio.setWsqLr(bDto.getWsqLr());
        bio.setWsqLs(bDto.getWsqLs());
        bio.setWsqRt(bDto.getWsqRt());
        bio.setWsqRi(bDto.getWsqRi());
        bio.setWsqRm(bDto.getWsqRm());
        bio.setWsqRr(bDto.getWsqRr());
        bio.setWsqRs(bDto.getWsqRs());
        bio.setNoFingerPrint(noFingerprint);
        bio.setNoFingerprintReason(reason);
        bio.setNoFingerprintReasonText(reasonTxt);
        return bio;
    }

}
