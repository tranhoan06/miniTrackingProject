package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressSnapshotResponse implements Serializable {
    private String receiverName;
    private String phone;

    private Long provinceId;
    private String provinceName;

    private Long districtId;
    private String districtName;

    private Long wardId;
    private String wardName;

    private String detailAddress;
}
