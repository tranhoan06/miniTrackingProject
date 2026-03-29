package com.example.miniTrackingProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "addreses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddresesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "receiver_name", length = 255)
    private String receiverName;

    @Column(length = 10)
    private String phone;

    @Column(name = "province_id", nullable = false)
    private Long provinceId;

    @Column(name = "province_name", length = 255, nullable = false)
    private String provinceName;

    @Column(name = "districtId", nullable = false)
    private Long districtId;

    @Column(name = "district_name", length = 255, nullable = false)
    private String districtName;

    @Column(name = "ward_id", nullable = false)
    private Long wardId;

    @Column(name = "ward_name", length = 255, nullable = false)
    private String wardName;

    @Column(name = "detail_address", length = 500)
    private String detailAddress;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
