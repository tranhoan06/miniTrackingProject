package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "k dc de trong")
    private String name;

    @NotBlank(message = "k dc de trong")
    private String code;

    @NotBlank(message = "k dc de trong")
    private String phone;

    @NotBlank(message = "k dc de trong")
    private String email;

    @NotBlank(message = "k dc de trong")
    private String website;

    private Boolean isActive = true;
}
