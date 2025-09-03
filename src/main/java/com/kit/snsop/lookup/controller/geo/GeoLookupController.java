package com.kit.snsop.lookup.controller.geo;

import com.kit.snsop.lookup.payload.geo.GetLookupRequest;
import com.kit.snsop.lookup.payload.geo.GetLookupResponse;
import com.kit.snsop.lookup.service.geo.GeoLookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lookup/geo")
@RequiredArgsConstructor
public class GeoLookupController {

    private final GeoLookupService geoLookupService;

    @PostMapping("/search")
    public @ResponseBody GetLookupResponse getLookup(@RequestBody GetLookupRequest request) {
        return geoLookupService.getLookup(request);
    }
}