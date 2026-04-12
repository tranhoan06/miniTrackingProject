package com.example.miniTrackingProject.dto.request;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewOrderRequest implements Serializable {

    @NonNull
    private BigDecimal subtotal;

    @NonNull
    private Long voucherId;
}
