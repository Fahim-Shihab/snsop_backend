package com.kit.snsop.beneficiary.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity{

    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Date created;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private Date updated;

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    private Long updatedBy;

    @Version
    @Column(name = "version")
    private Long version;
}
