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

public class ValidationException extends BaseException{

    public ValidationException() {
        super(ErrorCode.VALIDATION_ERROR);
    }
    
    public ValidationException(String message){
        super(ErrorCode.VALIDATION_ERROR, message);
    }
    
}
