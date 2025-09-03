package com.kit.snsop.approval.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovalTaskCreationResponse {
    private Long taskId;
    private String responseStatus;
    private String reason;
}
