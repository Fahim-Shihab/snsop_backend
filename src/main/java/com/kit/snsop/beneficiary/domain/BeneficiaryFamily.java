package com.kit.snsop.beneficiary.domain;

import com.kit.snsop.beneficiary.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "P2_BENEFICIARY_FAMILY")
@Data
@NoArgsConstructor
public class BeneficiaryFamily extends BaseEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CURRENCY", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CurrencyEnum currency;

    @Column(name = "HOUSEHOLD_INCOME_SOURCE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private IncomeSourceEnum householdIncomeSource;

    @Column(name = "INCOME_SOURCE_OTHER", length = 100)
    private String incomeSourceOther;

    @Column(name = "HOUSEHOLD_MONTHLY_AVG_INCOME", nullable = false)
    private Integer householdMonthlyAvgIncome;

    @Column(name = "HOUSEHOLD_SIZE", nullable = false)
    private Integer householdSize;

    @Column(name = "IS_OTHER_MEMBER_PARTICIPATING", nullable = false)
    private Boolean isOtherMemberPerticipating;

    @Column(name = "NON_PARTICIPATION_REASON")
    @Enumerated(EnumType.ORDINAL)
    private NonPerticipationReasonEnum notPerticipationReason;

    @Column(name = "NON_PARTICIPATION_OTHER_REASON")
    private String notPerticipationOtherReason;

    @Column(name = "MEMBER_CAN_READ_WRITE", nullable = false)
    private Integer memberReadWrite;

    @Column(name = "RELATIONSHIP_WITH_HOUSEHOLD", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RelationshipEnum relationshipWithHouseholdHead;

    @Column(name = "RELATIONSHIP_OTHER", length = 100)
    private String relationshipOther;

    @Column(name = "SPOUSE_FIRST_NAME", length = 100)
    private String spouseFirstName;

    @Column(name = "SPOUSE_MIDDLE_NAME", length = 100)
    private String spouseMiddleName;

    @Column(name = "SPOUSE_LAST_NAME", length = 100)
    private String spouseLastName;

    @Column(name = "SPOUSE_NICK_NAME", length = 100)
    private String spouseNickName;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BENEFICIARY_ID")
    private Beneficiary beneficiary;
}
