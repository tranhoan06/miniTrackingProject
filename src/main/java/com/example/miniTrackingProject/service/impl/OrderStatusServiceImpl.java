package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.dto.request.OrderStatusLogRequest;
import com.example.miniTrackingProject.dto.response.OrderStatusLogResponse;
import com.example.miniTrackingProject.repository.OrderStatusLogRepository;
import com.example.miniTrackingProject.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusLogRepository orderStatusLogRepository;

    @Override
    public OrderStatusLogResponse getLog(OrderStatusLogRequest request) {
        return null;
    }
}
