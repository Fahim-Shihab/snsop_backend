package com.kit.snsop.beneficiary.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {
    private String applicationId;
    private String url;
    private String etag;
}
