package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.*;
import com.example.miniTrackingProject.dto.request.DeliveredOrderRequest;
import com.example.miniTrackingProject.dto.request.ShippingOrderRequest;
import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;
import com.example.miniTrackingProject.entity.OrderStatusLogEntity;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.ShippingProviderEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.ShippingProviderMapper;
import com.example.miniTrackingProject.repository.OrderRepository;
import com.example.miniTrackingProject.repository.OrderStatusLogRepository;
import com.example.miniTrackingProject.repository.ShippingProviderRepository;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.ShippingProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShippingProviderServiceImpl implements ShippingProviderService {
    private final ShippingProviderRepository shippingProviderRepository;
    private final ShippingProviderMapper shippingProviderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SecurityHelper securityHelper;
    private final OrderStatusLogRepository orderStatusLogRepository;

    private void logOrderStatusChange(OrdersEntity order, OrderStatus from, OrderStatus to, String note, UserEntity changedBy) {
        if (order == null || order.getId() == null) return;
        if (from == to) return;

        OrderStatusLogEntity log = new OrderStatusLogEntity();
        log.setOrderId(order);
        log.setStatus(to);
        log.setNote(note);

        String changer = (changedBy != null && changedBy.getFullname() != null && !changedBy.getFullname().isBlank())
                ? changedBy.getFullname()
                : (changedBy != null ? changedBy.getUsername() : null);
        log.setChangedBy(changer);

        orderStatusLogRepository.save(log);
    }

    @Override
    public ShippingProviderResponse getAllById(Long id) {
        ShippingProviderEntity shippingProvider = shippingProviderRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        ShippingProviderResponse response = shippingProviderMapper.toResponse(shippingProvider);

        return response;
    }

    @Override
    @Transactional
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

        OrderStatus from = order.getOrderStatus();
        order.setShipper(shipper);
        order.setOrderStatus(OrderStatus.IN_TRANSIT);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        logOrderStatusChange(order, from, OrderStatus.IN_TRANSIT, "SHIPPER_START", shipper);
        return "Shipping started successfully";
    }

    @Override
    @Transactional
    public String deliveredOrder(DeliveredOrderRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        OrdersEntity order = orderRepository.findByTrackingCode(request.getTrackingCode())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));

        boolean isShipper = order.getShipper() != null && order.getShipper().getId().equals(user.getId());

        if (!isShipper) {
            throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
        }

        if (!order.getOrderStatus().equals(OrderStatus.IN_TRANSIT)) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }

        OrderStatus from = order.getOrderStatus();
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        logOrderStatusChange(order, from, OrderStatus.DELIVERED, request.getNote(), user);

        return "Giao hàng thành công";
    }

}
