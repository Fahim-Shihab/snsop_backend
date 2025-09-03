package com.kit.snsop.approval.repository;

import com.kit.snsop.approval.domain.ApprovalTask;
import com.kit.snsop.beneficiary.domain.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovalTaskRepository extends JpaRepository<ApprovalTask, Long>, JpaSpecificationExecutor<Nominee> {

}
