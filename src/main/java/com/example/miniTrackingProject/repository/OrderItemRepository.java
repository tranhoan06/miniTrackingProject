package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemsEntity, Long> {
}
