package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewOrderResponse implements Serializable {

    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal total;
}
