package com.kit.snsop.beneficiary.payload;

import lombok.Data;

import java.util.List;

@Data
public class BatchRegistrationResponse {
    private List<String> applicationIds;
}
