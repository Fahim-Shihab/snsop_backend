package com.kit.snsop.beneficiary.model;

import com.kit.snsop.beneficiary.domain.Boma;
import com.kit.snsop.beneficiary.domain.County;
import com.kit.snsop.beneficiary.domain.Payam;
import com.kit.snsop.beneficiary.domain.States;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeoDto implements Serializable {

    String id;
    String name;

    public GeoDto(Object[] data) {
        if (data != null && data.length >=2) {
            if (data[0] != null) {
                if (data[0] instanceof String) {
                    this.id = (String) data[0];
                } else {
                    this.id = data[0] +"";
                }
            }
            if (data[1] != null) {
                this.name = (String) data[1];
            }
        }
    }

    public GeoDto(States data) {
        if (data != null) {
            this.id = data.getId()+"";
            this.name = data.getName();
        }
    }
    public GeoDto(County data) {
        if (data != null) {
            this.id = data.getId()+"";
            this.name = data.getName();
        }
    }

    public GeoDto(Payam data) {
        if (data != null) {
            this.id = data.getId()+"";
            this.name = data.getName();
        }
    }

    public GeoDto(Boma data) {
        if (data != null) {
            this.id = data.getId()+"";
            this.name = data.getName();
        }
    }
}

