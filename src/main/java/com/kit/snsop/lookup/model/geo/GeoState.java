package com.kit.snsop.lookup.model.geo;

import com.kit.snsop.idm.model.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "geo_state")
@Getter
@Setter
public class GeoState implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private UserInfo createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private UserInfo modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Version
    @Column(name = "version")
    private Integer version;
}