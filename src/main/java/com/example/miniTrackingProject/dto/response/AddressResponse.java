package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse implements Serializable {
    private Long id;
    private Long user;

    private String receiverName;

    private String phone;

    private Long provinceId;

    private String provinceName;

    private Long districtId;

    private String districtName;

    private Long wardId;

    private String wardName;

    private String detailAddress;

    private Boolean isDefault;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
