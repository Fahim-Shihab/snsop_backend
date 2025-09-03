package com.kit.snsop.beneficiary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author anwar
 */
@Entity
@Table(name = "P2_BENEFICIARY_HOUSEHOLD")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdInfo extends BaseEntity {
    
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "MALE_NORMAL")
    private Integer maleTotal;
    
    @Column(name = "MALE_BOTH")
    private Integer maleBoth;
    
    @Column(name = "MALE_DISABLED")
    private Integer maleDisable;
    
    @Column(name = "MALE_CHRONICALLY_ILL")
    private Integer maleChronicalIll;
    
    @Column(name = "FEMALE_NORMAL")
    private Integer femaleTotal;
    
    @Column(name = "FEMALE_BOTH")
    private Integer femaleBoth;
    
    @Column(name = "FEMALE_DISABLED")
    private Integer femaleDisable;
    
    @Column(name = "FEMALE_CHRONICALLY_ILL")
    private Integer femaleChronicalIll;

    @Column(name = "MINIMUM_AGE", nullable = false)
    private Integer minimumAge;

    @Column(name = "MAXIMUM_AGE", nullable = false)
    private Integer maximumAge;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BENEFICIARY_ID")
    private Beneficiary beneficiary;
}
