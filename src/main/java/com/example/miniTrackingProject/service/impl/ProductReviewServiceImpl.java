package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.common.SecurityHelper;
import com.example.miniTrackingProject.dto.request.ProductReviewRequest;
import com.example.miniTrackingProject.entity.ProductReviewsEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.repository.ProductRepository;
import com.example.miniTrackingProject.repository.ProductReviewRepository;
import com.example.miniTrackingProject.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final SecurityHelper securityHelper;
    private final ProductRepository productRepository;

    @Override
    public String create(ProductReviewRequest request) {
        UserEntity user = securityHelper.getCurrentUser();

        if(!user.getRole().equals(RoleEnum.BUYER)) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }
        ProductsEntity productsEntity = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductReviewsEntity productReviewsEntity = new ProductReviewsEntity();
        productReviewsEntity.setProduct(productsEntity);
        productReviewsEntity.setBuyer(user);
        productReviewsEntity.setRating(request.getRating());
        productReviewsEntity.setContent(request.getContent());
        productReviewsEntity.setIsAnonymous(request.getIsAnonymous());
        productReviewsEntity.setCreatedAt(LocalDateTime.now());
        productReviewsEntity.setIsDelete(false);
        productReviewRepository.save(productReviewsEntity);
        return "Đánh giá thành công";
    }
}
