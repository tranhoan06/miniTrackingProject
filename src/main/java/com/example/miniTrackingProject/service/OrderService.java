package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.request.PreviewOrderRequest;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.dto.response.PreviewOrderResponse;

public interface OrderService {

    PreviewOrderResponse previewOrder(PreviewOrderRequest request);

    String createOrder(OrderRequest request);
}
