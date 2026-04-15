package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingProviderResponse implements Serializable {
    private Long id;

    private String name;

    private String code;

    private String phone;

    private String email;

    private String website;

    private Boolean isActive = true;

    private List<UserResponse> shipper;
}
