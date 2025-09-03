package com.kit.snsop.lookup.service.geo;

import com.kit.snsop.beneficiary.domain.GeoLookup;
import com.kit.snsop.beneficiary.model.GeoLookupDto;
import com.kit.snsop.beneficiary.repository.GeoLookupRepository;
import com.kit.snsop.common.exceptions.ValidationException;
import com.kit.snsop.lookup.payload.geo.GeoDto;
import com.kit.snsop.lookup.payload.geo.GetLookupRequest;
import com.kit.snsop.lookup.payload.geo.GetLookupResponse;
import com.kit.snsop.lookup.repository.geo.GeoBomaRepository;
import com.kit.snsop.lookup.repository.geo.GeoCountyRepository;
import com.kit.snsop.lookup.repository.geo.GeoPayamRepository;
import com.kit.snsop.lookup.repository.geo.GeoStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoLookupService {

    private final GeoStateRepository geoStateRepo;
    private final GeoCountyRepository geoCountyRepo;
    private final GeoPayamRepository geoPayamRepo;
    private final GeoBomaRepository geoBomaRepo;
    private final GeoLookupRepository geoLookupRepo;

    @Cacheable(value = "geoLookup", key = "{#request.lookupType, #request.parentId, #request.size}")
    public GetLookupResponse getLookup(GetLookupRequest request) {
        try {
            List<GeoDto> result = switch (request.getLookupType()) {
                case STATE -> getStates();
                case COUNTY -> getCounties(request.getParentId());
                case PAYAM -> getPayams(request.getParentId());
                case BOMA -> getBomas(request.getParentId());
            };

            return new GetLookupResponse(result, result.size());
        } catch (Exception ex) {
            return new GetLookupResponse(false, "Failed to load lookup: " + ex.getMessage());
        }
    }

    @Cacheable("geoStates")
    public List<GeoDto> getStates() {
        return geoStateRepo.findAll().stream()
                .map(GeoDto::new)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "geoCountiesByState", key = "#stateId")
    public List<GeoDto> getCounties(String stateId) {
        System.out.println("🚨 Fetching from DB for stateId = " + stateId);
        return geoCountyRepo.findByStateId(Integer.parseInt(stateId)).stream()
                .map(GeoDto::new)
                .collect(Collectors.toList());
    }


    @Cacheable(value = "geoPayamsByCounty", key = "#countyId")
    public List<GeoDto> getPayams(String countyId) {
        if (countyId == null) return List.of();
        return geoPayamRepo.findByCountyId(Integer.parseInt(countyId)).stream()
                .map(GeoDto::new)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "geoBomasByPayam", key = "#payamId")
    public List<GeoDto> getBomas(String payamId) {
        if (payamId == null) return List.of();
        return geoBomaRepo.findByPayamId(Integer.parseInt(payamId)).stream()
                .map(GeoDto::new)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "geolookup", key = "#bCode")
    public GeoLookupDto getGeoLookup(Long bCode) {

        Optional<GeoLookup> lookupOp = geoLookupRepo.findGeoLookupBybCode(bCode);

        if (!lookupOp.isPresent()) {
            throw new ValidationException("Invalid boma id");
        }

        GeoLookup entity = lookupOp.get();
        return new GeoLookupDto(entity);
    }

}