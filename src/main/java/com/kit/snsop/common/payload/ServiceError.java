package com.kit.snsop.common.payload;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServiceError implements Serializable {

    private String errorMsg;
    private Integer errorCode;

    public ServiceError(Integer errorCode, String details) {
        this.errorCode = errorCode;
        String codeName = ErrorCode.getValue(errorCode).name();
        this.errorMsg = codeName + " [" + details + "]";
    }
}
