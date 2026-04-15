package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingOrderRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private Long shipperId;
}
