package com.kit.snsop.beneficiary.payload;
import com.kit.snsop.beneficiary.model.BeneficiaryDto;
import lombok.Data;
import java.util.List;

@Data
public class BatchRegistrationRequest {
    private List<BeneficiaryDto> beneficiaries;
}
