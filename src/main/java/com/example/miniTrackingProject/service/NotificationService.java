package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;

public interface NotificationService {
    void notifyOrderStatus(UserEntity recipient, OrdersEntity order, OrderStatus status);
}
