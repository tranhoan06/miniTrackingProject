package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewOrderRequest implements Serializable {
    @NotNull
    private List<OrderItemRequest> items;

    private Long voucherId;

    private Boolean isBuyNow = false;
}
