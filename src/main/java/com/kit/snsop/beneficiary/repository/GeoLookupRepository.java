package com.kit.snsop.beneficiary.repository;

import com.kit.snsop.beneficiary.domain.GeoLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeoLookupRepository extends JpaRepository<GeoLookup, Integer>, JpaSpecificationExecutor<GeoLookup> {
    Optional<GeoLookup> findGeoLookupBybCode(Long bCode);
    Optional<GeoLookup> findGeoLookupByStateAndCountyAndPayamAndBoma(String state, String county, String payam, String boma);
}
