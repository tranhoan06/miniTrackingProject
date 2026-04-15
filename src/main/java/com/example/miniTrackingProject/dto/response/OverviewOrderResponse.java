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
public class OverviewOrderResponse implements Serializable {
    private BigDecimal totalPrice;
    private Long totalOrder;
    private Long totalPending;
    private Long totalIntransit;
    private Long totalCancel;
    private Long totalReturn; // (Đơn hàng đang trong quá trình đổi trả)
    private Long awaitingInspection; // (Đang chờ kiểm tra)
    private Long totalRefunds; // (Tổng số tiền hoàn lại)
}
