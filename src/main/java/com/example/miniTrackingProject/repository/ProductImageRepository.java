package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImagesEntity, Long> {
}
