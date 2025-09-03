package com.kit.snsop.approval.domain;

import com.kit.snsop.beneficiary.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "p2_task_details")
@Data
public class TaskDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_data", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String taskData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "APPROVAL_TASK_ID")
    private ApprovalTask approvalTask;
}
