package com.kit.snsop.beneficiary.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "GEO_LOOKUP")
@Data
public class GeoLookup {
    @Id
    @Column(name = "ROWID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "S_CODE")
    private Integer sCode;

    @Column(name = "STATE")
    private String state;

    @Column(name = "C_CODE")
    private Integer cCode;

    @Column(name = "COUNTY")
    private String county;

    @Column(name = "P_CODE")
    private Integer pCode;

    @Column(name = "PAYAM")
    private String payam;

    @Column(name = "B_CODE")
    private Long bCode;

    @Column(name = "BOMA")
    private String boma;
}

