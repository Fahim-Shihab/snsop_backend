package com.kit.snsop.beneficiary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.snsop.approval.enums.ApprovalTaskTypeEnum;
import com.kit.snsop.approval.payload.ApprovalTaskCreationRequest;
import com.kit.snsop.approval.service.ApprovalTaskService;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import com.kit.snsop.beneficiary.payload.BatchRegistrationResponse;
import com.kit.snsop.beneficiary.payload.BeneficiaryEditResponse;
import com.kit.snsop.beneficiary.service.BeneficiaryEditPublisher;
import com.kit.snsop.beneficiary.service.BeneficiaryRegistrationPublisher;
import com.kit.snsop.beneficiary.service.BeneficiaryService;
import com.kit.snsop.common.util.Defs;
import com.kit.snsop.common.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/beneficiary/update")
@AllArgsConstructor
public class BeneficiaryUpdateController {

    private final ApprovalTaskService approvalTaskService;
    private final ObjectMapper objectMapper;

    @PostMapping("/temp")
    public BeneficiaryEditResponse editBeneficiaryTemp(@Valid @RequestBody BeneficiaryDto.BeneficiaryEditRequest request) throws Exception {
        try {
            String taskData = objectMapper.writeValueAsString(request);
            ApprovalTaskCreationRequest approvalRequest = ApprovalTaskCreationRequest.builder()
                    .approvalTaskClassName("beneficiaryEdit")
                    .approvalTaskType(ApprovalTaskTypeEnum.BENEFICIARY_EDIT)
                    .taskData(taskData)
                    .approverRole("ADMIN")
                    .build();
            approvalTaskService.createApprovalTask(approvalRequest);
        } catch (Exception ex) {
            log.error("Failed to process request with application id {}", request.getApplicationId(), ex);
            return BeneficiaryEditResponse.builder().responseStatus(Defs.RESPONSE_STATUS_FAIL).reason(ex.getMessage()).build();
        }
        return BeneficiaryEditResponse.builder().responseStatus(Defs.RESPONSE_STATUS_SUCCESS).build();
    }

}
