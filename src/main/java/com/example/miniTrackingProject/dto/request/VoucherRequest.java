package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.DiscountType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class VoucherRequest implements Serializable {

    @NotEmpty(message = "Không được để trống")
    private String code;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private BigDecimal discountValue = BigDecimal.ZERO;

    @NotNull
    private Integer quantity;

    private String description;

    private BigDecimal maxDiscount;

    @NotNull
    private BigDecimal minOrderAmount;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;
}
