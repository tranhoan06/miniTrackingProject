package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.PayMethodEnum;
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

    private Long voucher;

    private Long address;

    private String orderNote;

    private PayMethodEnum paymentStatus;

    private List<OrderItemRequest> items;
}
