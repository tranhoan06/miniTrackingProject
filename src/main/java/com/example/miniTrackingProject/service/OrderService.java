package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.*;
import com.example.miniTrackingProject.dto.response.OrderStatusResponse;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.dto.response.PreviewOrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {

    PreviewOrderResponse previewOrder(PreviewOrderRequest request);

    String createOrder(OrderRequest request);

    Page<OrderResponse> getBySeller(Integer pageSize, Integer pageNumber);

    OrderStatusResponse confirmOrder(ConfirmStatusRequest request);

    OrderStatusResponse cancelOrder(CancelOrderRequest request);

    OrderStatusResponse packedOrder(OrderStatusRequest request);
}
