package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest implements Serializable {
    @NotNull
    private Long user;

    @NotEmpty(message = "Không được để trống")
    private String receiverName;

    @NotEmpty(message = "Không được để trống")
    private String phone;

    @NotNull
    private Long provinceId;

    @NotEmpty(message = "Không được để trống")
    private String provinceName;

    @NotNull
    private Long districtId;

    @NotEmpty(message = "Không được để trống")
    private String districtName;

    @NotNull
    private Long wardId;

    @NotEmpty(message = "Không được để trống")
    private String wardName;

    @NotEmpty(message = "Không được để trống")
    private String detailAddress;

    private Boolean isDefault;
}
