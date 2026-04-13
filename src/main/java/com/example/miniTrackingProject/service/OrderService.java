package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.request.PreviewOrderRequest;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.dto.response.PreviewOrderResponse;

import java.util.List;

public interface OrderService {

    PreviewOrderResponse previewOrder(PreviewOrderRequest request);

    String createOrder(OrderRequest request);

    List<OrderResponse> getOrderBySeller(Long sellerId);
}
