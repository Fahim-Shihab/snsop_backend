package com.kit.snsop.beneficiary.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.snsop.beneficiary.domain.RequestLog;
import com.kit.snsop.beneficiary.enums.OperationResult;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import com.kit.snsop.beneficiary.repository.RequestLogRepository;
import com.kit.snsop.common.util.Defs;
import com.kit.snsop.filter.RequestUserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kit.snsop.common.util.Defs.DEFAULT_USER_ID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeneficiaryRegistrationConsumer {

    private final BeneficiaryService service;
    private final RequestLogRepository requestLogRepository;
    private final ObjectMapper objectMapper;


    @RabbitListener(queues = "${kit.rabbit.registration.queue}")
    public void receiveMessage(Message message, BeneficiaryDto beneficiaryRequest)
    {
        try {
            Long userId = (Long) message.getMessageProperties().getHeaders().get(Defs.HEADER.X_USER_ID);
            if (userId == null) {
                userId = DEFAULT_USER_ID;
            }
            RequestUserContext.setUser(userId);
            Optional<RequestLog> requestLogOp = requestLogRepository.findByRequestId(beneficiaryRequest.getRequestId());
            RequestLog requestLog = requestLogOp.orElse(null);
            try {
                log.info("msg received = {}", beneficiaryRequest.getApplicationId());
                BeneficiaryDto beneficiary = service.getBeneficiary(beneficiaryRequest.getApplicationId());
                if (beneficiary != null) {
                    log.info("Beneficiary with application id {} already exists", beneficiaryRequest.getApplicationId());
                    return;
                }
                service.saveBeneficiary(beneficiaryRequest);
                if (requestLog != null) {
                    requestLog.setStatus(OperationResult.SUCCESS);
                    requestLogRepository.save(requestLog);
                }
            } catch (Throwable th) {
                log.error(th.getMessage(), th);

                if (requestLog != null) {
                    String jsonData = "";
                    try {
                        jsonData = objectMapper.writeValueAsString(beneficiaryRequest);
                    } catch (JsonProcessingException jpe) {
                        log.error("e", jpe);
                    }

                    requestLog.setStatus(OperationResult.FAILED);
                    requestLog.setRequestData(jsonData);
                    requestLogRepository.save(requestLog);
                }
                log.error("===REQUEST SAVING PROBLEM IN DATABASE WITH APPLICATION ID:{}", beneficiaryRequest.getApplicationId());
            }
        }finally {
            RequestUserContext.clear();
        }
    }

}
