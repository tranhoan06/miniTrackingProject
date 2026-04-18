package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.OrderStatusLogRequest;
import com.example.miniTrackingProject.dto.response.OrderStatusLogResponse;
import com.example.miniTrackingProject.entity.OrderStatusLogEntity;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.OrderStatusLogMapper;
import com.example.miniTrackingProject.repository.OrderRepository;
import com.example.miniTrackingProject.repository.OrderStatusLogRepository;
import com.example.miniTrackingProject.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusLogRepository orderStatusLogRepository;
    private final OrderStatusLogMapper orderStatusLogMapper;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderStatusLogResponse> getLog(OrderStatusLogRequest request) {
        OrdersEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));

        List<OrderStatusLogEntity> logEntityList =
                orderStatusLogRepository.findByOrder(order);

        if(logEntityList.isEmpty()) {
            throw new JavaBuilderException(ErrorCode.NOT_FOUND);
        }
        return orderStatusLogMapper.toResponse(logEntityList);
    }
}
