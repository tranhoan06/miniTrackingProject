package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.entity.OrdersEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusLogResponse implements Serializable {
    private Long orderId;

    private OrderStatus status;

    private String note;
    private String changedBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}
