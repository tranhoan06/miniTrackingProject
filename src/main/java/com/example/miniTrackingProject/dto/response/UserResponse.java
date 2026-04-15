package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.entity.AddresesEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements Serializable {
    private Long id;

    private String username;

    private String fullname;

    private RoleEnum role;

    private List<AddresesEntity> addresses;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long shippingProviderId;
}
