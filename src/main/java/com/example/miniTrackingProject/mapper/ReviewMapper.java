package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.ProductReviewResponse;
import com.example.miniTrackingProject.entity.ProductReviewsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "product.id", target = "productId")
    ProductReviewResponse toResponse(ProductReviewsEntity entity);

    @AfterMapping
    default void handleAnonymous(@MappingTarget ProductReviewResponse response, ProductReviewsEntity entity) {
        if (Boolean.TRUE.equals(entity.getIsAnonymous())) {
            response.setBuyerName("Người dùng ẩn danh");
            response.setBuyerId(null);
        } else {
            response.setBuyerName(entity.getBuyer().getFullname());
        }
    }
}
