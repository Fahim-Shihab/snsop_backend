package com.kit.snsop.lookup.payload.geo;

import com.kit.snsop.common.payload.ServiceResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetLookupResponse extends ServiceResponse implements Serializable {

    List<GeoDto> lookups;
    long totalCount;

    public GetLookupResponse(boolean status, String message) {
        super(status, message);
    }

    public GetLookupResponse(List<GeoDto> lookups, long totalCount) {
        super(true, null); // success
        this.lookups = lookups;
        this.totalCount = totalCount;
    }
}
