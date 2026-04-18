package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingProviderRequest implements Serializable {
    @NotEmpty(message = "k dc de trong")
    private String name;

    @NotEmpty(message = "k dc de trong")
    private String code;

    @NotEmpty(message = "k dc de trong")
    private String phone;

    @NotEmpty(message = "k dc de trong")
    private String email;

    @NotEmpty(message = "k dc de trong")
    private String website;

    private Boolean isActive = true;
}
