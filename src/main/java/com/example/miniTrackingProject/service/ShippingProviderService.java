package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.DeliveredOrderRequest;
import com.example.miniTrackingProject.dto.request.ShippingOrderRequest;
import com.example.miniTrackingProject.dto.request.ShippingProviderRequest;
import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;

import java.util.List;

public interface ShippingProviderService {
    ShippingProviderResponse create(ShippingProviderRequest request);
    ShippingProviderResponse update(Long id, ShippingProviderRequest request);
    List<ShippingProviderResponse> getAll();

    ShippingProviderResponse getById(Long id);

    String shippingOrder(ShippingOrderRequest request);

    String deliveredOrder(DeliveredOrderRequest request);
}
