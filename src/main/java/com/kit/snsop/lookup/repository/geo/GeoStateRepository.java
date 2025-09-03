package com.kit.snsop.lookup.repository.geo;

import com.kit.snsop.lookup.model.geo.GeoState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoStateRepository extends JpaRepository<GeoState, Integer> {

}