package com.kit.snsop.approval.repository;

import com.kit.snsop.approval.domain.TaskDetails;
import com.kit.snsop.beneficiary.domain.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailRepository extends JpaRepository<TaskDetails, Long>, JpaSpecificationExecutor<Nominee> {

}
