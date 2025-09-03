/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;

import java.io.Serializable;

import com.kit.snsop.beneficiary.enums.BiometricType;
import com.kit.snsop.beneficiary.enums.BiometricUserType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author anwar
 */
@Data
public class FileUploadDto implements Serializable{
    
    @NotEmpty
    private String applicationId;
    
    @NotNull
    private BiometricType biometricType;
    
    @NotNull
    private BiometricUserType biometricUserType;
    
    private Integer sequence;

}
