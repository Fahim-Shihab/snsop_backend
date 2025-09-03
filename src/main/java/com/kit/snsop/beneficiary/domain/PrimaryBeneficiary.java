package com.kit.snsop.beneficiary.domain;

import com.kit.snsop.beneficiary.enums.*;
import com.kit.snsop.beneficiary.model.AlternateDto;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "tbl_BeneficiaryServiceDet")
@Data
@NoArgsConstructor
public class PrimaryBeneficiary {

    @Id
    @Column(name = "intBenTempid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "household_number", nullable = false, length = 100)
    private String applicationId;

    @Column(name = "Age", nullable = false)
    private Integer respondentAge;

    @Column(name = "Sex", nullable = false)
    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum respondentGender;

    @Column(name = "LegalStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private LegalStatusEnum respondentLegalStatus;

    @Column(name = "NationalID", length = 50)
    private String respondentId;

    @Column(name = "Email", length = 50)
    private String email;

    @Column(name = "ContactNo", length = 10)
    private String respondentPhoneNo;

    @Column(name = "SOurceofIncome", nullable = false)
    @Enumerated(EnumType.STRING)
    private IncomeSourceEnum householdIncomeSource;

    //add from beneficiary
    @Column(name = "INCOME_SOURCE_OTHER", length = 100)
    private String incomeSourceOther;

    @Column(name = "FamilyIncome", nullable = false)
    private Integer householdMonthlyAvgIncome;

    @Column(name = "SelectionCriteria", nullable = false)
    @Enumerated(EnumType.STRING)
    private SelectionCriteriaEnum selectionCriteria;

    @Column(name = "SelectionReason", nullable = false)
    private String selectionReasons;

    @Column(name = "FamilySize", nullable = false)
    private Integer householdSize;

    //add from beneficiary
    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    @Column(name = "Created_dt", nullable = false)
    private Date created;

    @Column(name = "Modified_dt", nullable = false)
    private Date updated;

    @Column(name = "Created_by", nullable = false)
    private Long createdBy;

    @Column(name = "Modified_by")
    private Long updatedBy;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiary_id", referencedColumnName = "ID", nullable = false)
    private Beneficiary beneficiary;

    @Column(name = "OLDEnrollmentNo")
    private String oldEnrollmentNo;

    @Column(name = "EnrollmentNo")
    private String enrollmentNo;

    //new
    @Column(name = "NameofHousehold", nullable = false, length = 100)
    private String nameOfHousehold;

    @Column(name = "NoofDependents", nullable = false)
    private Integer noOfDependents;

    //new
    @Column(name = "intStateid")
    private Integer stateId;

    //new
    @Column(name = "intCountieid")
    private Integer countyId;

    //new
    @Column(name = "intPayamid")
    private Integer payamId;

    //new
    @Column(name = "intBomasid")
    private Integer bomaId;

    //new
    @Column(name = "BenStatus", nullable = false)
    private String benStatus;

    //new
    @Column(name = "BeneficiaryType")
    private String beneficiaryType;

    //new
    @Column(name = "Name1stAlternatePayee")
    private String name1stAlternatePayee;

    //new
    @Column(name = "FirstAlternatePayeeGender")
    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum firstAlternatePayeeGender;

    //new
    @Column(name = "FirstAlternatePayeeAge")
    private Integer firstAlternatePayeeAge;

    //new
    @Column(name = "FirstAlternatePayeeNationID")
    private String firstAlternatePayeeNationID;

    //new
    @Column(name = "FirstAlternatePayeeMobile")
    private String firstAlternatePayeeMobile;

    //new
    @Column(name = "FirstAlternatePayeePhotoName")
    private String firstAlternatePayeePhotoName;

    //new
    @Column(name = "FirstAlternatePayeePhotoPath")
    private String firstAlternatePayeePhotoPath;

    //new
    @Column(name = "Name2ndAlternatePayee")
    private String Name2ndAlternatePayee;

    //new
    @Column(name = "SecondAlternatePayeeGender")
    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum secondAlternatePayeeGender;

    //new
    @Column(name = "SecondAlternatePayeeAge")
    private Integer secondAlternatePayeeAge;

    //new
    @Column(name = "SecondAlternateNationalID")
    private String secondAlternatePayeeNationID;

    //new
    @Column(name = "secondAlternatePayeeMobile")
    private String secondAlternatePayeeMobile;

    //new
    @Column(name = "SecondAlternatePayeePhotoName")
    private String secondAlternatePayeePhotoName;

    //new
    @Column(name = "SecondAlternatePayeePhotoPath")
    private String secondAlternatePayeePhotoPath;

    //new
    @Column(name = "NumberAdultsAgeAbove65")
    private Integer numberAdultsAgeAbove65;

    //new
    @Column(name = "NumberAdultsAgeBetween45_64")
    private Integer numberAdultsAgeBetween45_64;

    //new
    @Column(name = "NumberAdultsAgeBetween30_45")
    private Integer numberAdultsAgeBetween30_45;

    //new
    @Column(name = "NumberChildrenAgeAbove5years")
    private Integer numberChildrenAgeAbove5years;

    //new
    @Column(name = "NumberChildrenAgeAbove18years")
    private Integer numberChildrenAgeAbove18years;

    //new
    @Column(name = "PCPortFlag")
    private String pcPortFlag;

    //new
    @Column(name = "household_photo_url")
    private String householdPhotoUrl;

    //new
    @Column(name = "alt1_photo_url")
    private String alt1PhotoUrl;

    //new
    @Column(name = "alt2_photo_url")
    private String alt2PhotoUrl;

    //new
    @Column(name = "NameofSpouse", length = 100)
    private String nameOfSpouse;

    //new
    @Column(name = "intIP")
    private Integer intIP;

    //new
    @Column(name = "female_dependants")
    private Integer femaleDependants;

    //new
    @Column(name = "male_dependants")
    private Integer maleDependants;

    //new
    @Column(name = "member_number")
    private Integer memberNumber;

    //new
    @Column(name = "latitude")
    private String latitude;

    //new
    @Column(name = "longitude")
    private String longitude;

    //new
    @Column(name = "TransferDate")
    private Date transferDate;

    //new
    @Column(name = "TransferUpdateat")
    private Date transferUpdateat;

    //new
    @Column(name = "alternate_number")
    private String alternateNumber;

    //new
    @Column(name = "Secondalternate_number")
    private String secondAlternateNumber;

    //new
    @Column(name = "API_Status")
    private String apiStatus;

    public PrimaryBeneficiary(BeneficiaryDto beneficiaryDto) {
        customFieldSet(this, beneficiaryDto);
        this.getBeneficiary().setEsSyncStatus(1);
        this.getBeneficiary().setStatus(StatusEnum.ACTIVE);
        this.getBeneficiary().setAfisStatus(AfisStatusEnum.PENDING);
        this.setStatus(StatusEnum.ACTIVE);
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setVersion(1);
    }

    private void customFieldSet(PrimaryBeneficiary primaryBeneficiary, BeneficiaryDto beneficiaryDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.map(beneficiaryDto, this);

        Beneficiary beneficiary = new Beneficiary(beneficiaryDto);
        primaryBeneficiary.setBeneficiary(beneficiary);

        primaryBeneficiary.setIntIP(1001);
        primaryBeneficiary.setPcPortFlag("N");
        primaryBeneficiary.setEnrollmentNo(UUID.randomUUID().toString());
        primaryBeneficiary.setNameOfHousehold(beneficiaryDto.getRespondentFirstName() + " " + beneficiaryDto.getRespondentMiddleName() + " " + beneficiaryDto.getRespondentLastName());
        primaryBeneficiary.setNameOfSpouse(beneficiaryDto.getSpouseFirstName() + " " + beneficiaryDto.getSpouseMiddleName() + " " + beneficiaryDto.getSpouseLastName());


        Integer zeroFive = 0;
        Integer maleTotal = 0;
        Integer femaleTotal = 0;
        if (beneficiaryDto.getHouseholdMember2() != null) {
            maleTotal += beneficiaryDto.getHouseholdMember2().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember2().getMaleTotal() : 0;
            femaleTotal += beneficiaryDto.getHouseholdMember2().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember2().getFemaleTotal() : 0;
            zeroFive = (beneficiaryDto.getHouseholdMember2().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember2().getMaleTotal() : 0)
                    + (beneficiaryDto.getHouseholdMember2().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember2().getFemaleTotal() : 0);

        }
        if (beneficiaryDto.getHouseholdMember5() != null) {
            maleTotal += beneficiaryDto.getHouseholdMember5().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember5().getMaleTotal() : 0;
            femaleTotal += beneficiaryDto.getHouseholdMember5().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember5().getFemaleTotal() : 0;

            zeroFive += (beneficiaryDto.getHouseholdMember5().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember5().getMaleTotal() : 0)
                    + (beneficiaryDto.getHouseholdMember5().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember5().getFemaleTotal() : 0);

        }
        primaryBeneficiary.setNumberChildrenAgeAbove5years(zeroFive);

        Integer sixEighteen = 0;
        if (beneficiaryDto.getHouseholdMember17() != null) {
            maleTotal += beneficiaryDto.getHouseholdMember17().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember17().getMaleTotal() : 0;
            femaleTotal += beneficiaryDto.getHouseholdMember17().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember17().getFemaleTotal() : 0;

            sixEighteen = (beneficiaryDto.getHouseholdMember17().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember17().getMaleTotal() : 0)
                    + (beneficiaryDto.getHouseholdMember17().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember17().getFemaleTotal() : 0);
        }

        Integer nineteenFortyFive = 0;
        if (beneficiaryDto.getHouseholdMember35() != null) {
            maleTotal += beneficiaryDto.getHouseholdMember35().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember35().getMaleTotal() : 0;
            femaleTotal += beneficiaryDto.getHouseholdMember35().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember35().getFemaleTotal() : 0;

            nineteenFortyFive = (beneficiaryDto.getHouseholdMember35().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember35().getMaleTotal() : 0)
                    + (beneficiaryDto.getHouseholdMember35().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember35().getFemaleTotal() : 0);
        }

        primaryBeneficiary.setNumberAdultsAgeBetween30_45(nineteenFortyFive);

        Integer fortySixSixtyFive = 0;
        if (beneficiaryDto.getHouseholdMember64() != null) {
            maleTotal += beneficiaryDto.getHouseholdMember64().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember64().getMaleTotal() : 0;
            femaleTotal += beneficiaryDto.getHouseholdMember64().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember64().getFemaleTotal() : 0;

            fortySixSixtyFive = (beneficiaryDto.getHouseholdMember64().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember64().getMaleTotal() : 0)
                    + (beneficiaryDto.getHouseholdMember64().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember64().getFemaleTotal() : 0);
        }

        primaryBeneficiary.setNumberAdultsAgeBetween45_64(fortySixSixtyFive);


        Integer sixtySix = 0;
        if (beneficiaryDto.getHouseholdMember65() != null) {
            maleTotal += beneficiaryDto.getHouseholdMember65().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember65().getMaleTotal() : 0;
            femaleTotal += beneficiaryDto.getHouseholdMember65().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember65().getFemaleTotal() : 0;

            sixtySix = (beneficiaryDto.getHouseholdMember65().getMaleTotal() != null ? beneficiaryDto.getHouseholdMember65().getMaleTotal() : 0)
                    + (beneficiaryDto.getHouseholdMember65().getFemaleTotal() != null ? beneficiaryDto.getHouseholdMember65().getFemaleTotal() : 0);

        }

        primaryBeneficiary.setNumberAdultsAgeAbove65(sixtySix);
        primaryBeneficiary.setNoOfDependents(femaleTotal + maleTotal);
        primaryBeneficiary.setMaleDependants(maleTotal);
        primaryBeneficiary.setFemaleDependants(femaleTotal);
        primaryBeneficiary.setTransferDate(new Date());
        primaryBeneficiary.setTransferUpdateat(new Date());
        primaryBeneficiary.setApiStatus("Success");


        if(beneficiaryDto.getAddress() != null) {
            primaryBeneficiary.setStateId(beneficiaryDto.getAddress().getStateId());
            primaryBeneficiary.setCountyId(beneficiaryDto.getAddress().getCountyId());
            primaryBeneficiary.setPayamId(beneficiaryDto.getAddress().getPayam());
            if(!StringUtils.isEmpty(beneficiaryDto.getAddress().getBoma())) {
                primaryBeneficiary.setBomaId(Integer.parseInt(beneficiaryDto.getAddress().getBoma()));
            }
        }
        primaryBeneficiary.setBenStatus("1");
        primaryBeneficiary.setBeneficiaryType("1");

        if(beneficiaryDto.getLocation() != null) {
            primaryBeneficiary.setLatitude(beneficiaryDto.getLocation().getLat() != null ? beneficiaryDto.getLocation().getLat().toString() : "0");
            primaryBeneficiary.setLongitude(beneficiaryDto.getLocation().getLon() != null ? beneficiaryDto.getLocation().getLon().toString() : "0");
        }

        if(beneficiaryDto.getAlternatePayee1() != null) {
            AlternateDto alternateDto = beneficiaryDto.getAlternatePayee1();
            primaryBeneficiary.setAlternateNumber(UUID.randomUUID().toString());
            primaryBeneficiary.setName2ndAlternatePayee(alternateDto.getPayeeFirstName() + " " + alternateDto.getPayeeMiddleName() + " " + alternateDto.getPayeeLastName());
            primaryBeneficiary.setFirstAlternatePayeeAge(alternateDto.getPayeeAge());
            primaryBeneficiary.setFirstAlternatePayeeNationID(alternateDto.getNationalId());
            primaryBeneficiary.setFirstAlternatePayeeMobile(alternateDto.getPayeePhoneNo());
            if(beneficiaryDto.getAlternatePayee1() != null && beneficiaryDto.getAlternatePayee1().getBiometric() != null){
                primaryBeneficiary.setFirstAlternatePayeePhotoPath(beneficiaryDto.getAlternatePayee1().getBiometric().getPhoto());
                primaryBeneficiary.setAlt1PhotoUrl(beneficiaryDto.getAlternatePayee1().getBiometric().getPhoto());
            }
        }

        if(beneficiaryDto.getAlternatePayee2() != null) {
            AlternateDto alternateDto = beneficiaryDto.getAlternatePayee2();
            primaryBeneficiary.setSecondAlternateNumber(UUID.randomUUID().toString());
            primaryBeneficiary.setName2ndAlternatePayee(alternateDto.getPayeeFirstName() + " " + alternateDto.getPayeeMiddleName() + " " + alternateDto.getPayeeLastName());
            primaryBeneficiary.setSecondAlternatePayeeAge(alternateDto.getPayeeAge());
            primaryBeneficiary.setSecondAlternatePayeeNationID(alternateDto.getNationalId());
            primaryBeneficiary.setSecondAlternatePayeeMobile(alternateDto.getPayeePhoneNo());
            if(beneficiaryDto.getAlternatePayee2() != null && beneficiaryDto.getAlternatePayee2().getBiometric() != null){
                primaryBeneficiary.setSecondAlternatePayeePhotoPath(beneficiaryDto.getAlternatePayee2().getBiometric().getPhoto());
                primaryBeneficiary.setAlt2PhotoUrl(beneficiaryDto.getAlternatePayee2().getBiometric().getPhoto());
            }
        }

        primaryBeneficiary.setMemberNumber(beneficiaryDto.getHouseholdSize());

        if(beneficiaryDto.getSelectionReason() != null){
            StringBuilder sb = new StringBuilder();
            for(SelectionReasonEnum selectionEnum : beneficiaryDto.getSelectionReason()){
                if(selectionEnum != null){
                    sb.append(selectionEnum.name());
                    sb.append(",");
                }
            }
            primaryBeneficiary.setSelectionReasons(sb.toString());
        }
    }

}
