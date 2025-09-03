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
@Table(name = "LOCATION")
@Data
public class Location {
    
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "LAT")
    private Double lat;
    
    @Column(name = "LON")
    private Double lon;
}
