/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kit.snsop.common.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author anwar
 */
@Data
@NoArgsConstructor
public class BaseException extends RuntimeException{
    private ErrorCode error;
    private String errorCode;
    private String errorMessage;
    
    public BaseException(ErrorCode error){
        super(error.getErrorMessage());
        this.errorCode = error.getErrorCode();
        this.errorMessage = error.getErrorMessage();
    }
    
    public BaseException(ErrorCode error, String errorMessage){
        super(errorMessage);
        this.errorCode = error.getErrorCode();
        this.errorMessage = errorMessage;
    }
}
