package com.kit.snsop.beneficiary.model;

import java.io.Serializable;

import com.kit.snsop.beneficiary.domain.Location;
import com.kit.snsop.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author anwar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto implements Serializable{
    private Double lat;
    private Double lon;

    public LocationDto(Location location) {
        if (location != null) {
            this.lat = location.getLat();
            this.lon = location.getLon();
        }
    }

    public LocationDto(Object[] esObject) {
        if (esObject != null) {
            if (Utils.indexValueExists(esObject,34)) {
                this.lat = (Double) esObject[34];
            }
            if (Utils.indexValueExists(esObject, 35)) {
                this.lon = (Double) esObject[35];
            }
        }
    }
}
