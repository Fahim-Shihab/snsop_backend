package com.kit.snsop.common.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class ServiceResponse implements Serializable {

    boolean operationResult = true;
    String errorMsg;
    Integer errorCode;

    public ServiceResponse(ServiceError error) {
        this.operationResult = false;
        this.errorMsg = error.getErrorMsg();
        this.errorCode = error.getErrorCode();
    }

    public ServiceResponse(boolean operationResult, String errorMsg) {
        this.operationResult = operationResult;
        this.errorMsg = errorMsg;
    }
}
