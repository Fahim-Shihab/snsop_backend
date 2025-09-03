package com.kit.snsop.approval.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovalResponse {
    private String responseStatus;
    private String reason;
}
