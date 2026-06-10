package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductReviewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReviewsEntity, Long> {
    Page<ProductReviewsEntity> findByProductIdAndIsDeleteFalse(Long productId, Pageable pageable);

    @EntityGraph(attributePaths = {"buyer"})
    @Query("SELECT r FROM ProductReviewsEntity r " +
            "WHERE r.product.id = :productId AND r.isDelete = false")
    Page<ProductReviewsEntity> findByProductIdWithBuyer(
            @Param("productId") Long productId,
            Pageable pageable);

    Optional<ProductReviewsEntity> findByBuyerIdAndProductId(Long buyerId, Long productId);

    @EntityGraph(attributePaths = {"buyer"})
    Optional<ProductReviewsEntity> findWithBuyerById(Long id);
}
