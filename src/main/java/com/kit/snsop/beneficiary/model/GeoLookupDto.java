/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;

import java.io.Serializable;

import com.kit.snsop.beneficiary.domain.GeoLookup;
import lombok.Data;

/**
 *
 * @author anwar
 */
@Data
public class GeoLookupDto implements Serializable{
    private Integer id;
    private Integer sCode;
    private String state;
    private Integer cCode;
    private String county;
    private Integer pCode;
    private String payam;
    private Long bCode;
    private String boma;

    public GeoLookupDto() {
    }
    
    public GeoLookupDto(GeoLookup entity) {
        if(entity != null){
            this.id = entity.getId();
            this.sCode = entity.getSCode();
            this.state = entity.getState();
            this.cCode = entity.getCCode();
            this.county = entity.getCounty();
            this.pCode = entity.getPCode();
            this.payam = entity.getPayam();
            this.bCode = entity.getBCode();
            this.boma = entity.getBoma();
        }
    }
}
