package com.kit.snsop.lookup.payload.geo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.lang.reflect.Method;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeoDto implements Serializable {

    String id;
    String name;

    public GeoDto(Object data) {
        if (data == null) return;

        try {
            Method getIdMethod = data.getClass().getMethod("getId");
            Method getNameMethod = data.getClass().getMethod("getName");

            Object idValue = getIdMethod.invoke(data);
            Object nameValue = getNameMethod.invoke(data);

            if (idValue != null) this.id = idValue.toString();
            if (nameValue != null) this.name = nameValue.toString();

        } catch (Exception e) {
            throw new RuntimeException("GeoDto mapping failed for: " + data.getClass().getSimpleName(), e);
        }
    }
}