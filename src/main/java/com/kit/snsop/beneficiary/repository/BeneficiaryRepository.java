package com.kit.snsop.beneficiary.repository;

import com.kit.snsop.beneficiary.domain.Beneficiary;
import com.kit.snsop.beneficiary.enums.AfisStatusEnum;
import com.kit.snsop.beneficiary.enums.GenderEnum;
import com.kit.snsop.beneficiary.enums.SelectionCriteriaEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long>, JpaSpecificationExecutor<Beneficiary> {
}
