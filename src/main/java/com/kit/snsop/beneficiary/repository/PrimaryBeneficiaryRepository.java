package com.kit.snsop.beneficiary.repository;

import com.kit.snsop.beneficiary.domain.PrimaryBeneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrimaryBeneficiaryRepository extends JpaRepository<PrimaryBeneficiary, Integer>, JpaSpecificationExecutor<PrimaryBeneficiary> {
    Optional<PrimaryBeneficiary> findByApplicationId(String applicationId);
}
