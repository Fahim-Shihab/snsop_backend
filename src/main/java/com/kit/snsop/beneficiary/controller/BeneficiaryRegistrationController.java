package com.kit.snsop.beneficiary.controller;

import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import com.kit.snsop.beneficiary.payload.BatchRegistrationRequest;
import com.kit.snsop.beneficiary.payload.BatchRegistrationResponse;
import com.kit.snsop.beneficiary.service.BeneficiaryRegistrationPublisher;
import com.kit.snsop.beneficiary.service.BeneficiaryService;
import com.kit.snsop.common.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/beneficiary/register/")
@AllArgsConstructor
public class BeneficiaryRegistrationController {

    private final HttpServletRequest httpServletRequest;
    private final BeneficiaryRegistrationPublisher publisher;
    private final BeneficiaryService beneficiaryService;

    @PostMapping("/batch")
    public BatchRegistrationResponse registerBatch(@Valid @RequestBody BatchRegistrationRequest request) throws Exception {

        Long userId = Utils.getUserIdFromHeader(httpServletRequest);

        BatchRegistrationResponse response = new BatchRegistrationResponse();
        List<String> ids = new ArrayList<>();

        for (BeneficiaryDto dto : request.getBeneficiaries()) {
            try {
                dto.setCreatedBy(userId);
                dto.setUpdatedBy(userId);
                beneficiaryService.validate(dto);
                publisher.sendMessage(dto);
            } catch (Exception ex) {
                log.error("Failed to process request with application id {}", dto.getApplicationId(), ex);
                continue;
            }
            ids.add(dto.getApplicationId());
        }
        response.setApplicationIds(ids);
        return response;
    }

}
