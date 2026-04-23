package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.NotificationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationsRepository extends JpaRepository<NotificationsEntity, Long> {
    List<NotificationsEntity> findTop50ByDeliveryStatusOrderByIdAsc(String deliveryStatus);

    boolean existsByOrders_IdAndTemplates_StatusCodeAndDeliveryStatusAndNotificationType(
            Long orderId,
            String statusCode,
            String deliveryStatus,
            String notificationType
    );
}
