package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.dto.request.ShippingOrderRequest;
import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.ShippingProviderEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.ShippingProviderMapper;
import com.example.miniTrackingProject.repository.OrderRepository;
import com.example.miniTrackingProject.repository.ShippingProviderRepository;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.ShippingProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShippingProviderServiceImpl implements ShippingProviderService {
    private final ShippingProviderRepository shippingProviderRepository;
    private final ShippingProviderMapper shippingProviderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public ShippingProviderResponse getAllById(Long id) {
        ShippingProviderEntity shippingProvider = shippingProviderRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        ShippingProviderResponse response = shippingProviderMapper.toResponse(shippingProvider);

        return response;
    }

    @Override
    public String shippingOrder(ShippingOrderRequest request) {
        OrdersEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));

        UserEntity shipper = userRepository.findById(request.getShipperId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        if (order.getOrderStatus() != OrderStatus.SHIPPED) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }
        if (shipper.getRole() != RoleEnum.SHIPPER) {
            throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
        }

        order.setShipper(shipper);
        order.setOrderStatus(OrderStatus.IN_TRANSIT);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        return "Shipping started successfully";
    }

}
