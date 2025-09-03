package com.kit.snsop.beneficiary.repository;

import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiaryESRepository extends ElasticsearchRepository<BeneficiaryDto, String> {
}
