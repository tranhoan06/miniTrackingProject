package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverviewResponse implements Serializable {
    private Long totalProduct;
    private BigDecimal totalPriceProuct;
    private Long totalActiveProduct;
    private Long totalInactiveProduct;
    private Long totalDraftProduct;
}
