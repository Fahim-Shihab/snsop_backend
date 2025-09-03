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

public class ServiceException extends BaseException{
    public ServiceException() {
        super(ErrorCode.SERVICE_ERROR);
    }
    
    public ServiceException(String message){
        super(ErrorCode.SERVICE_ERROR, message);
    }
}
