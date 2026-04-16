package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.StatusProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class FilterProductRequest implements Serializable {

    private String keyword;
    private StatusProduct status;
    private BigDecimal minPrice;

    private BigDecimal maxPrice;
}
