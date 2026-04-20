package com.example.miniTrackingProject.repository.projection;

import java.math.BigDecimal;

public interface OrderOverviewProjection {
    BigDecimal getTotalAmount();
    Long getTotalOrder();       // COUNT(*) — dùng Long cho khỏi lệch kiểu; nếu bắt Integer thì cast trong service
    Long getTotalPending();
    Long getTotalIntransit();
    Long getTotalCancel();
    Long getTotalFailed();
    Long getTotalReturn();
    Long getAwaitingInspection();
    Long getTotalPriceRefunds();
    Long getTotalComplete();
}
