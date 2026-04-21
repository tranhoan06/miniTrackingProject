package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductReviewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviewsEntity, Long> {
    Page<ProductReviewsEntity> findByProductIdAndIsDeleteFalse(Long productId, Pageable pageable);

    @Query(value = "SELECT * FROM product_reviews " +
            "WHERE product_id = :productId AND is_delete = false " +
            "ORDER BY created_at DESC " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<ProductReviewsEntity> findReviewsWithOffset(
            @Param("productId") Long productId,
            @Param("limit") int limit,
            @Param("offset") int offset);

    Optional<ProductReviewsEntity> findByBuyerIdAndProductId(Long buyerId, Long productId);
}
