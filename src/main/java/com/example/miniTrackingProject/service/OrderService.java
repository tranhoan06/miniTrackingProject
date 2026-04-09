package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);
}
