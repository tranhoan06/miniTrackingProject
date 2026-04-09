package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.DiscountType;
import com.example.miniTrackingProject.common.StatusVoucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherResponse implements Serializable {
    private Long id;

    private String code;

    private String description;

    private Long seller;

    private DiscountType discountType;

    private BigDecimal discountValue = BigDecimal.ZERO;

    private BigDecimal maxDiscount;

    private BigDecimal minOrderAmount;

    private Integer quantity;

    private Integer usedCount = 0;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private StatusVoucher status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
