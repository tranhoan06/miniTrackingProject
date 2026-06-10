package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.NotificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationsEntity, Long> {

    boolean existsByOrders_IdAndTemplates_StatusCodeAndDeliveryStatusAndNotificationType(
            Long orderId,
            String statusCode,
            String deliveryStatus,
            String notificationType
    );

    boolean existsByOrders_IdAndTemplates_StatusCodeAndNotificationType(Long orderId, String statusCode, String notificationType);
}
