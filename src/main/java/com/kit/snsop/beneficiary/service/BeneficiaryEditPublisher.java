package com.kit.snsop.beneficiary.service;

import com.kit.snsop.beneficiary.domain.RequestLog;
import com.kit.snsop.beneficiary.enums.LogTypeEnum;
import com.kit.snsop.beneficiary.enums.OperationResult;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import com.kit.snsop.beneficiary.repository.RequestLogRepository;
import com.kit.snsop.common.util.Defs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class BeneficiaryEditPublisher {
    @Value(value = "${kit.rabbit.edit.exchange}")
    private String exchange;

    @Value(value = "${kit.rabbit.edit.routing-key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RequestLogRepository requestLogRepository;

    public void sendMessage(BeneficiaryDto.BeneficiaryEditRequest msg) {
        log.info("Edit request with application id {}", msg.getApplicationId());
        RequestLog requestLog = logRequest(msg);
        msg.setRequestId(requestLog.getRequestId());

        MessageProperties props = new MessageProperties();
        props.setHeader(Defs.HEADER.X_USER_ID, msg.getUpdatedBy());

        try {
            Message message = rabbitTemplate.getMessageConverter().toMessage(msg, props);

            rabbitTemplate.send(exchange, routingKey, message);
            log.info("msg published = {}", msg.getApplicationId());
        } catch (AmqpException ampqEx) {
            throw new RuntimeException(ampqEx);
        }
    }

    private RequestLog logRequest(BeneficiaryDto.BeneficiaryEditRequest msg) {
        RequestLog requestLog = new RequestLog();
        requestLog.setRequestId(UUID.randomUUID().toString());
        requestLog.setApplicationId(msg.getApplicationId());
        requestLog.setRequestDate(new Date());
        requestLog.setOperationType(LogTypeEnum.UPDATE);
        requestLog.setStatus(OperationResult.PENDING);
        requestLog.setUpdatedBy(msg.getUpdatedBy());
        requestLogRepository.save(requestLog);
        return requestLog;
    }

}
