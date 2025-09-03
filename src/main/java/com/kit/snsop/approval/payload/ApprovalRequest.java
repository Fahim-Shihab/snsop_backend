package com.kit.snsop.approval.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovalRequest {
    private Long approvalTaskId;
    private boolean approved;
}
