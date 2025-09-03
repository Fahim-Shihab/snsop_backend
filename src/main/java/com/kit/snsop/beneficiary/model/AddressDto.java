/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;

import java.io.Serializable;

import com.kit.snsop.beneficiary.domain.Address;
import com.kit.snsop.common.util.Utils;
import lombok.Data;

/**
 *
 * @author anwar
 */
@Data
public class AddressDto implements Serializable{
    private Integer stateId;
    private Integer countyId;
    private Integer payam;
    private String boma;

    public AddressDto() {

    }

    public AddressDto(Address address) {
        this.stateId = address.getStateId();
        this.countyId = address.getCountyId();
        this.payam = address.getPayam();
        this.boma = Utils.isNull(address.getBoma()) ? null : address.getBoma()+"";
    }
    
}
