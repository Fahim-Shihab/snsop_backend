package com.kit.snsop.approval.service;

import com.kit.snsop.approval.payload.ApprovalRequest;
import com.kit.snsop.approval.payload.ApprovalResponse;
import com.kit.snsop.approval.payload.ApprovalTaskCreationRequest;
import com.kit.snsop.approval.payload.ApprovalTaskCreationResponse;

public interface ApprovalTaskService {
    ApprovalTaskCreationResponse createApprovalTask(ApprovalTaskCreationRequest approvalTaskCreationRequest);
    ApprovalResponse doApprove(ApprovalRequest approvalRequest);
}
