package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {
}
