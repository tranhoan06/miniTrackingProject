package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.repository.OrderRepository;
import com.example.miniTrackingProject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        return null;
    }
}
