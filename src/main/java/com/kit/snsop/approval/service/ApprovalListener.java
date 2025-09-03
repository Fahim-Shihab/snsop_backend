package com.kit.snsop.approval.service;

import com.kit.snsop.approval.domain.ApprovalTask;

public interface ApprovalListener {
    void onApprove(ApprovalTask approvalTask);
    void onReject(ApprovalTask approvalTask);
}
