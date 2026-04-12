package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.CartEntity;
import com.example.miniTrackingProject.entity.CartItemsEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemsEntity, Long> {
    @Query("select c from CartItemsEntity c " + "where c.cart = :cart and c.product = :product and c.isDelete = false")
    Optional<CartItemsEntity> findByCartAndProduct(@Param("cart") CartEntity cart, @Param("product") ProductsEntity product);
}
