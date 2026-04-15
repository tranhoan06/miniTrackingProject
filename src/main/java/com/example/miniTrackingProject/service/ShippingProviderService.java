package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.ShippingOrderRequest;
import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;

public interface ShippingProviderService {

    ShippingProviderResponse getAllById(Long id);

    String shippingOrder(ShippingOrderRequest request);
}
