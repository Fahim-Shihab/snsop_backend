/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.beneficiary.model;

import java.io.Serializable;
import com.kit.snsop.beneficiary.enums.BiometricUserType;
import com.kit.snsop.beneficiary.enums.NoFingerprintReason;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author anwar
 */
@Data
public class BiometricDto implements Serializable{
    
    @NotEmpty(message = "Application ID is empty")
    private String applicationId;

    @NotNull(message = "Biometric User Type is null")
    private BiometricUserType biometricUserType;

    private boolean noFingerPrint;
    private NoFingerprintReason noFingerprintReason;
    private String noFingerprintReasonText;

    @NotNull(message = "Photo is null")
    private byte[] photoData;
    private byte[] wsqLtData;
    private byte[] wsqLiData;
    private byte[] wsqLmData;
    private byte[] wsqLrData;
    private byte[] wsqLsData;
    private byte[] wsqRtData;
    private byte[] wsqRiData;
    private byte[] wsqRmData;
    private byte[] wsqRrData;
    private byte[] wsqRsData;

    private String photo;
    private String wsqLt;
    private String wsqLi;
    private String wsqLm;
    private String wsqLr;
    private String wsqLs;
    private String wsqRt;
    private String wsqRi;
    private String wsqRm;
    private String wsqRr;
    private String wsqRs;
}
