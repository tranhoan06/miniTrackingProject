package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.*;
import com.example.miniTrackingProject.dto.request.PaymentRequest;
import com.example.miniTrackingProject.entity.CartItemsEntity;
import com.example.miniTrackingProject.entity.OrderItemsEntity;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.repository.CartItemRepository;
import com.example.miniTrackingProject.repository.OrderRepository;
import com.example.miniTrackingProject.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final OrderRepository orderRepository;
    private final SecurityHelper securityHelper;
    private final CartItemRepository cartItemRepository;

    @Override
    public String payment(PaymentRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        if (request.getPayMethod().equals(PayMethodEnum.PREPAY)) {
            List<OrdersEntity> ordersEntityList = orderRepository.findByIdAndBuyerAndOrderStatusAndPaymentMethodAndPaymentStatus(
                    request.getOrderIds(),
                    user,
                    OrderStatus.PENDING,
                    PayMethodEnum.PREPAY,
                    PaymentStatus.PENDING
            );

            if (ordersEntityList.size() != request.getOrderIds().size()) {
                throw new JavaBuilderException(ErrorCode.SOME_ORDERS_NOT_FOUND_OR_INVALID);
            }

            for(OrdersEntity orders: ordersEntityList) {
                orders.setPaymentStatus(PaymentStatus.PAID);
                orders.setOrderStatus(OrderStatus.CONFIRMED);
                // TODO: bỏ
//                for(OrderItemsEntity orderItems: orders.getItems()) {
//                    Long productId = orderItems.getProduct().getId();
//                    CartItemsEntity ci = cartItemRepository
//                            .findActiveByUserAndProduct(user.getId(), productId)
//                            .orElse(null);
//                    if (ci != null) {
//                        ci.setIsDelete(true);
//                        cartItemRepository.save(ci);
//                    }
//                }
            }
            orderRepository.saveAll(ordersEntityList);
        }

        return "Thanh toán thành công";
    }
}
