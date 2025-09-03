package com.kit.snsop.beneficiary.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.snsop.approval.domain.ApprovalTask;
import com.kit.snsop.approval.domain.TaskDetails;
import com.kit.snsop.approval.service.ApprovalListener;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import com.kit.snsop.common.exceptions.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("beneficiaryEdit")
@AllArgsConstructor
@Slf4j
public class BeneficiaryEditApprovalListener implements ApprovalListener {

    private final ObjectMapper objectMapper;
    private final BeneficiaryEditPublisher editPublisher;

    @Override
    public void onApprove(ApprovalTask approvalTask) {
        if(approvalTask == null){
            log.warn("Approval task is null");
            throw new ServiceException("Approval task is null");
        }

        TaskDetails taskDetails = approvalTask.getTaskDetails();
        if(taskDetails == null){
            log.warn("task details is null");
            throw new ServiceException("Task details is null");
        }

        String taskData = taskDetails.getTaskData();
        if(taskData == null || taskData.isEmpty()){
            log.warn("Task data is empty");
            throw new ServiceException("Task details is null");
        }

        BeneficiaryDto.BeneficiaryEditRequest beneficiaryEditRequest = objectMapper.convertValue(taskDetails.getTaskData(), BeneficiaryDto.BeneficiaryEditRequest.class);
        if(beneficiaryEditRequest == null){
            log.warn("beneficiaryEditRequest is null");
            throw new ServiceException("BeneficiaryEditRequest is null");
        }
        log.info("beneficiaryEditRequest :{}", beneficiaryEditRequest.getApplicationId());
        editPublisher.sendMessage(beneficiaryEditRequest);
        log.info("BeneficiaryEditApprovalListener send message success");
    }

    @Override
    public void onReject(ApprovalTask approvalTask) {

    }
}
