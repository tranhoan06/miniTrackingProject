package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.PayMethodEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {

    private Long voucherId;

    @NotNull
    private Long addressId;

    private String orderNote;

    @NotNull
    private PayMethodEnum paymentMethod;

    @NotNull
    private List<OrderItemRequest> items;
}
