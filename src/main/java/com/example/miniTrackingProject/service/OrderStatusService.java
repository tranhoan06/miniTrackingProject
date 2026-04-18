package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.OrderStatusLogRequest;
import com.example.miniTrackingProject.dto.response.OrderStatusLogResponse;

import java.util.List;

public interface OrderStatusService {
    List<OrderStatusLogResponse> getLog(OrderStatusLogRequest request);
}
