package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
}
