/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.common.exceptions;

/**
 *
 * @author anwar
 */

public enum ErrorCode {
    
    VALIDATION_ERROR("1001", "Invalid Data"),
    SERVICE_ERROR("1002", "Server error occurred");
    
    String errorMessage;
    String errorCode;
    
    ErrorCode(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
