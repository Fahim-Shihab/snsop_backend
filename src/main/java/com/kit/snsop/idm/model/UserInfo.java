package com.kit.snsop.idm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@Getter
@Setter
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 128)
    private String email;

    @Column(name = "status")
    private Integer status;

    @Column(name = "email_receiver")
    private Boolean emailReceiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleInfo role;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Version
    @Column(name = "version")
    private Integer version;
}