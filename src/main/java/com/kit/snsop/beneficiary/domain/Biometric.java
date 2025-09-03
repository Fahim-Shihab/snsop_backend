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
@Table(name = "P2_BIOMETRIC")
@Data
public class Biometric {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NO_FINGERPRINT")
    private boolean noFingerPrint;

    @Column(name = "NO_FINGERPRINT_REASON")
    @Enumerated(EnumType.ORDINAL)
    private NoFingerprintReason noFingerprintReason;

    @Column(name = "NO_FINGERPRINT_REASON_DETAILS", length = 255)
    private String noFingerprintReasonText;
    
    @Column(name = "WSQ_LT")
    private String wsqLt;
    
    @Column(name = "WSQ_LI")
    private String wsqLi;
    
    @Column(name = "WSQ_LM")
    private String wsqLm;
    
    @Column(name = "WSQ_LR")
    private String wsqLr;
    
    @Column(name = "WSQ_LS")
    private String wsqLs;
    
    @Column(name = "WSQ_RT")
    private String wsqRt;
    
    @Column(name = "WSQ_RI")
    private String wsqRi;
    
    @Column(name = "WSQ_RM")
    private String wsqRm;
    
    @Column(name = "WSQ_RR")
    private String wsqRr;
    
    @Column(name = "WSQ_RS")
    private String wsqRs;
    
    @Column(name = "PHOTO")
    private String photo;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ALTERNATE_ID", referencedColumnName = "ID")
    private Alternate alternate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BENEFICIARY_ID", referencedColumnName = "ID")
    private Beneficiary beneficiary;
}
