package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.OrderStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusLogRepository extends JpaRepository<OrderStatusLog, Long> {
}

