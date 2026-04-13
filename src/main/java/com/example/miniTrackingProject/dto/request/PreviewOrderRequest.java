package com.example.miniTrackingProject.dto.request;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreviewOrderRequest implements Serializable {

    @NonNull
    private List<Long> productId;

    @NonNull
    private Long voucherId;
}
