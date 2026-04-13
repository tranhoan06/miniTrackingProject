package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsResponse implements Serializable {
    private Long id;
    private Long productId;
    private String productNameSnapshot;
    private String productImageSnapshot;
    private Long sellerId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalAmount;
}

