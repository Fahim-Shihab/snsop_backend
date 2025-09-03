package com.kit.snsop.approval.domain;

import com.kit.snsop.approval.enums.ApprovalTaskStatusEnum;
import com.kit.snsop.approval.enums.ApprovalTaskTypeEnum;
import com.kit.snsop.beneficiary.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "p2_approval_task")
@Data
public class ApprovalTask extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @Column(name = "approver_role", nullable = false, length = 100)
    private String approverRole;

    @Column(name = "task_type", nullable = false, length = 100)
    @Enumerated(EnumType.ORDINAL)
    private ApprovalTaskTypeEnum taskType;

    @OneToOne(mappedBy = "approvalTask", cascade = CascadeType.ALL)
    private TaskDetails taskDetails;

    @Column(name = "approval_task_classname", nullable = false, length = 200)
    private String approvalTaskClassname;

    @Column(name = "approval_status", length = 50)
    @Enumerated(EnumType.ORDINAL)
    private ApprovalTaskStatusEnum approvalStatus;
}
