package com.kit.snsop.lookup.repository.geo;

import com.kit.snsop.lookup.model.geo.GeoCounty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GeoCountyRepository extends JpaRepository<GeoCounty, Integer> {

    Collection<Object> findByStateId(int i);
}