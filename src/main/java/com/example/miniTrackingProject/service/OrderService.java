package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.dto.request.*;
import com.example.miniTrackingProject.dto.response.*;
import org.springframework.data.domain.Page;

public interface OrderService {

    PreviewOrderResponse previewOrder(PreviewOrderRequest request);

    String createOrder(OrderRequest request);

    Page<OrderResponse> getBySeller(Integer pageSize, Integer pageNumber, Boolean isReturn, OrderStatus status);

    OrderStatusResponse confirmOrder(ConfirmStatusRequest request);

    OrderStatusResponse cancelOrder(CancelOrderRequest request);

    OrderStatusResponse packedOrder(OrderStatusRequest request);

    OrderStatusResponse assignProviderOrder(OrderStatusRequest request);

    OverviewOrderResponse overviewOrder(String type);

    OrderStatusResponse returnPendingOrder(CancelOrderRequest request);

    OrderStatusResponse returnOrder(OrderStatusRequest request);

    OrderStatusResponse warehouseReceivedOrder(OrderStatusRequest request);

    OrderStatusResponse restockedOrder(OrderStatusRequest request);

    OrderStatusResponse refundedOrder(OrderStatusRequest request);

    OrderResponse orderDetail(Long id);

    OverviewOrderResponse getOverviewUser();

    Page<OrderResponse> getByBuyer(Integer pageSize, Integer pageNumber, OrderStatus status);
}
