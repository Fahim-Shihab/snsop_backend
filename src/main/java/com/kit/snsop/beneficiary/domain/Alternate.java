/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.domain;

import com.kit.snsop.beneficiary.enums.*;
import jakarta.persistence.*;
import lombok.Data;
/**
 *
 * @author anwar
 */
@Entity
@Table(name = "P2_BENEFICIARY_ALTERNATE")
@Data
public class Alternate extends BaseEntity {
    
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alt_index")
    private Integer altIndex;
    
    @Column(name = "FIRST_NAME", length = 100, nullable = false)
    private String payeeFirstName;

    @Column(name = "MIDDLE_NAME", nullable = false, length = 100)
    private String payeeMiddleName;

    @Column(name = "LAST_NAME", length = 100, nullable = false)
    private String payeeLastName;

    @Column(name = "NICK_NAME", length = 100)
    private String payeeNickName;

    @Column(name = "FULL_NAME", length = 100, insertable = false, updatable = false)
    private String payeeFullName;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GenderEnum payeeGender;
    
    @Column(name = "AGE", nullable = false)
    private Integer payeeAge;
    
    @Column(name = "DOCUMENT_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private DocumentTypeEnum documentType;
    
    @Column(name = "DOCUMENT_TYPE_OTHER", length = 100)
    private String documentTypeOther;
    
    @Column(name = "RELATIONSHIP_WITH_HOUSEHOLD", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RelationshipEnum relationshipWithHouseholdHead;
    
    @Column(name = "RELATIONSHIP_OTHER", length = 100)
    private String relationshipOther;
    
    @Column(name = "NATIONAL_ID", length = 50)
    private String nationalId;
    
    @Column(name = "PHONE_NO", length = 10)
    private String payeePhoneNo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BENEFICIARY_ID")
    private Beneficiary beneficiary;

    @OneToOne(mappedBy = "alternate", cascade = CascadeType.ALL)
    private Biometric biometric;
}
