package com.kit.snsop.beneficiary.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kit.snsop.beneficiary.enums.LogTypeEnum;
import com.kit.snsop.beneficiary.enums.LogTypeEnumConverter;
import com.kit.snsop.beneficiary.enums.OperationResult;
import com.kit.snsop.beneficiary.enums.OperationResultEnumConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "REQUEST_LOG")
@Data
@NoArgsConstructor
public class RequestLog {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id", length = 50, nullable = false)
    private String applicationId;

    @Column(name = "request_id", length = 50)
    private String requestId;

    @Column(name = "request_date", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date requestDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "request_data", columnDefinition = "NVARCHAR(MAX)")
    private String requestData;

    @Column(name = "error_reason", columnDefinition = "TEXT")
    private String errorReason;

    @Column(name = "status", length = 50, nullable = false)
    @Convert(converter = OperationResultEnumConverter.class)
    private OperationResult status;

    @Column(name = "operation_type", length = 100)
    @Convert(converter = LogTypeEnumConverter.class)
    private LogTypeEnum operationType;


    @PrePersist
    private void setDefaultRequestDate() {
        if (this.requestDate == null) {
            this.requestDate = new Date();
        }
    }
}
