package com.kit.snsop.approval.service;

import com.kit.snsop.approval.domain.ApprovalTask;
import com.kit.snsop.approval.domain.TaskDetails;
import com.kit.snsop.approval.enums.ApprovalTaskStatusEnum;
import com.kit.snsop.approval.payload.ApprovalRequest;
import com.kit.snsop.approval.payload.ApprovalResponse;
import com.kit.snsop.approval.payload.ApprovalTaskCreationRequest;
import com.kit.snsop.approval.payload.ApprovalTaskCreationResponse;
import com.kit.snsop.approval.repository.ApprovalTaskRepository;
import com.kit.snsop.common.util.Defs;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ApprovalTaskServiceImpl implements ApprovalTaskService {

    private final ApprovalTaskRepository approvalTaskRepository;
    private final ApplicationContext applicationContext;

    @Override
    @Transactional()
    public ApprovalTaskCreationResponse createApprovalTask(ApprovalTaskCreationRequest approvalTaskCreationRequest) {

        log.info("Approval task creation request, task type {}", approvalTaskCreationRequest.getApprovalTaskType().name());

        TaskDetails taskDetails = new TaskDetails();
        ApprovalTask approvalTask = new ApprovalTask();

        approvalTask.setApproverRole(approvalTaskCreationRequest.getApproverRole());
        approvalTask.setTaskType(approvalTaskCreationRequest.getApprovalTaskType());
        approvalTask.setApprovalTaskClassname(approvalTaskCreationRequest.getApprovalTaskClassName());
        approvalTask.setApprovalStatus(ApprovalTaskStatusEnum.PENDING);

        taskDetails.setApprovalTask(approvalTask);
        taskDetails.setTaskData(approvalTaskCreationRequest.getTaskData());

        approvalTask.setTaskDetails(taskDetails);
        approvalTaskRepository.save(approvalTask);

        return ApprovalTaskCreationResponse.builder().responseStatus(Defs.RESPONSE_STATUS_SUCCESS).build();
    }

    @Override
    public ApprovalResponse doApprove(ApprovalRequest approvalRequest) {
        ApprovalTask approvalTask = approvalTaskRepository.getReferenceById(approvalRequest.getApprovalTaskId());
        if(approvalTask == null) {
            return ApprovalResponse.builder().responseStatus(Defs.RESPONSE_STATUS_FAIL).reason("Approval data not found").build();
        }

        log.info("Approval request for task id: {}, task type: {}, approval: {}", approvalRequest.getApprovalTaskId(), approvalTask.getTaskType().name(), approvalRequest.isApproved());

        String qualifier = approvalTask.getApprovalTaskClassname();
        ApprovalListener approvalListener = applicationContext.getBean(qualifier, ApprovalListener.class);
        if(approvalListener == null) {
            return ApprovalResponse.builder().responseStatus(Defs.RESPONSE_STATUS_FAIL).reason("No Approval Listener found for this approval process").build();
        }
        if(approvalRequest.isApproved()){
            approvalListener.onApprove(approvalTask);
            approvalTask.setApprovalStatus(ApprovalTaskStatusEnum.APPROVED);
        }else{
            approvalListener.onReject(approvalTask);
            approvalTask.setApprovalStatus(ApprovalTaskStatusEnum.REJECTED);
        }
        approvalTaskRepository.save(approvalTask);
        return ApprovalResponse.builder().responseStatus(Defs.RESPONSE_STATUS_SUCCESS).build();
    }
}
