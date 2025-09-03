package com.kit.snsop.lookup.repository.geo;

import com.kit.snsop.lookup.model.geo.GeoPayam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GeoPayamRepository extends JpaRepository<GeoPayam, Integer> {

    Collection<Object> findByCountyId(int i);
}