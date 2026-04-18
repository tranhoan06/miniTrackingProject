package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.OrderStatusLogEntity;
import com.example.miniTrackingProject.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLogEntity, Long> {
    List<OrderStatusLogEntity> findByOrder(OrdersEntity order);
}

