package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.OrderStatusLogRequest;
import com.example.miniTrackingProject.dto.response.OrderStatusLogResponse;

public interface OrderStatusService {
    OrderStatusLogResponse getLog(OrderStatusLogRequest request);
}
