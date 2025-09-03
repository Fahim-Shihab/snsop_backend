/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.domain;

import jakarta.persistence.*;
import lombok.Data;
/**
 *
 * @author anwar
 */
@Entity
@Table(name = "P2_BENEFICIARY_ADDRESS")
@Data
public class Address extends BaseEntity {
    
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "STATE_ID", nullable = false)
    private Integer stateId;
    
    @Column(name = "COUNTY_ID", nullable = false)
    private Integer countyId;
    
    @Column(name = "PAYAM_ID", nullable = false)
    private Integer payam;
    
    @Column(name = "BOMA_ID", nullable = false)
    private Long boma;

    @Column(name = "LATITUDE")
    private Double lat;

    @Column(name = "LONGITUDE")
    private Double lon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BENEFICIARY_ID")
    private Beneficiary beneficiary;
    
}
