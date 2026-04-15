package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.RoleEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class UserRequest implements Serializable {

    @NotEmpty(message = "Không được để trống")
    private String username;

    @NotEmpty(message = "Không được để trống")
    private String password;

    @NotEmpty(message = "Không được để trống")
    private String fullname;

    @NotNull(message = "Không được để trống")
    private RoleEnum role;

    private Long shippingProviderId;
}
