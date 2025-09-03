package com.kit.snsop.lookup.repository.geo;

import com.kit.snsop.lookup.model.geo.GeoBoma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GeoBomaRepository extends JpaRepository<GeoBoma, Integer> {

    Collection<Object> findByPayamId(int i);
}