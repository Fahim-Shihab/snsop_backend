package com.kit.snsop.beneficiary.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BeneficiaryEditResponse {
    private String responseStatus;
    private String reason;
}
