package com.kit.snsop.approval.payload;

import com.kit.snsop.approval.enums.ApprovalTaskTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovalTaskCreationRequest {
    private String approverRole;
    private ApprovalTaskTypeEnum approvalTaskType;
    private String approvalTaskClassName;
    private String taskData;
}
