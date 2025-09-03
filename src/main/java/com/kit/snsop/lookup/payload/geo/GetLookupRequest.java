package com.kit.snsop.lookup.payload.geo;

import com.kit.snsop.common.enums.LookupType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetLookupRequest implements Serializable {

    LookupType lookupType;
    String id;         // optional: could be used for direct lookup
    String parentId;   // for hierarchical filtering (e.g. county by state)
    String name;       // optional: for search filtering

    Integer from = 0;  // pagination start
    Integer size = 100; // default page size
}
