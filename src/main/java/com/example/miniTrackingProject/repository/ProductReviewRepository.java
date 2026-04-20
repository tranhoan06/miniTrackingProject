package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviewsEntity, Long> {
}
