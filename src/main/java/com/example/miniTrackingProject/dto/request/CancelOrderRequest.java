package com.example.miniTrackingProject.dto.request;

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
public class CancelOrderRequest implements Serializable {
    @NotNull
    private Long orderId;

    @NotEmpty(message = "Không được để trống")
    private String reason;
}
