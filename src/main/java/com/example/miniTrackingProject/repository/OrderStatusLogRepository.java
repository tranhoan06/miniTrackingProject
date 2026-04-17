package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.OrderStatusLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLogEntity, Long> {
}

