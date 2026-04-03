package com.example.miniTrackingProject.service.spec;

import com.example.miniTrackingProject.common.StatusProduct;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import com.example.miniTrackingProject.entity.ProductsEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<ProductsEntity> likeKeyword(String keyword) {
        return new Specification<ProductsEntity>() {
            // Predicate toPredicate: Method này dùng để tạo điều kiện WHERE trong SQL.
            // root đại diện cho entity ProductOffering.
            // CriteriaBuilder là API tạo câu SQL.
            // criteriaBuilder.conjunction(); điều kiện luôn đùng = where 1=1
            // | Method        | SQL  |
            // | ------------- | ---- |
            // | equal()       | =    |
            // | like()        | LIKE | gần giống
            // | greaterThan() | >    |
            // | lessThan()    | <    |
            // | conjunction() | true |
            @Override
            public @Nullable Predicate toPredicate(Root<ProductsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(keyword == null || keyword.isEmpty()) {
                    return criteriaBuilder.conjunction();
                }
                Predicate namePredicate = criteriaBuilder.like(root.get("productName"), "%" + keyword + "%");
                Predicate skuPredicate = criteriaBuilder.like(root.get("sku"), "%" + keyword + "%");
                return criteriaBuilder.or(namePredicate, skuPredicate);
            }
        };
    }

    // minPrice
    public static Specification<ProductsEntity> minPrice(BigDecimal minPrice) {
        return new Specification<ProductsEntity>() {
            @Override
            public @Nullable Predicate toPredicate(Root<ProductsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(minPrice == null) {
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
        };
    }

    // maxPrice
    public static Specification<ProductsEntity> maxPrice(BigDecimal maxPrice) {
        return new Specification<ProductsEntity>() {
            @Override
            public @Nullable Predicate toPredicate(Root<ProductsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(maxPrice == null) {
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
        };
    }

    // status
    public static Specification<ProductsEntity> filterStatus(StatusProduct statusProduct) {
        return new Specification<ProductsEntity>() {
            @Override
            public @Nullable Predicate toPredicate(Root<ProductsEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(statusProduct == null) {
                    return criteriaBuilder.conjunction();
                }
                return criteriaBuilder.equal(root.get("status"), statusProduct);
            }
        };
    }
}
