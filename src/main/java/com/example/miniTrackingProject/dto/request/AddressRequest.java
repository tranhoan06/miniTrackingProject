package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Không được để trống")
    private String receiverName;

    @NotBlank(message = "Không được để trống")
    @Size(max = 10, message = "Số điện thoại tối đa 10 ký tự")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotNull
    private Long provinceId;

    @NotBlank(message = "Không được để trống")
    private String provinceName;

    @NotNull
    private Long districtId;

    @NotBlank(message = "Không được để trống")
    private String districtName;

    @NotNull
    private Long wardId;

    @NotBlank(message = "Không được để trống")
    private String wardName;

    @NotEmpty(message = "Không được để trống")
    private String detailAddress;

    private Boolean isDefault;
}
