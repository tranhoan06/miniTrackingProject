package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.PayMethodEnum;
import com.example.miniTrackingProject.common.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {

    private Long id;

    private Long buyerId;
    private String buyerUsername;

    private Long sellerId;
    private String sellerUsername;

    private BigDecimal totalProductAmount;
    private BigDecimal shippingFee;
    private BigDecimal voucherDiscount;
    private BigDecimal finalAmount;

    private PayMethodEnum paymentMethod;
    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private Long voucherId;

    private Long addressId;
    private String shippingAddressSnapshot;

    private String orderNote;
    private LocalDateTime createdAt;

    private List<OrderItemsResponse> items;
}