package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.CartEntity;
import com.example.miniTrackingProject.entity.CartItemsEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemsEntity, Long> {
    Optional<CartItemsEntity> findByCartAndProduct(CartEntity cart, ProductsEntity product);
}
