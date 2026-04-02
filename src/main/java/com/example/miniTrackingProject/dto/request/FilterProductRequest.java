package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.StatusProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterProductRequest implements Serializable {

    private String keyword;
    private StatusProduct status;
    @JsonProperty("min_price")
    private Long minPrice;

    @JsonProperty("max_price")
    private Long maxPrice;
}
