package com.kit.snsop.beneficiary.service;

import com.kit.snsop.beneficiary.model.BeneficiaryDto;

public interface BeneficiaryService {
    void validate(BeneficiaryDto dto);
    void validate(BeneficiaryDto.BeneficiaryEditRequest dto);
    void saveBeneficiary(BeneficiaryDto dto);
    void updateBeneficiary(BeneficiaryDto.BeneficiaryEditRequest dto);
    BeneficiaryDto getBeneficiary(String applicationId);
}
