package com.example.miniTrackingProject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverviewOrderResponse implements Serializable {
    private BigDecimal totalAmount;
    private Long totalOrder;
    private Long totalPending;
    private Long totalIntransit;
    private Long totalCancel;
    private Long totalFailed;
    private Long totalReturn; // (Đơn hàng đang trong quá trình đổi trả)
    private Long awaitingInspection; // (Đang chờ kiểm tra)
    private Long totalPriceRefunds; // (Tổng số tiền hoàn lại)
    private Long totalComplete;
}
