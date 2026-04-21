package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.common.SecurityHelper;
import com.example.miniTrackingProject.dto.request.ProductReviewRequest;
import com.example.miniTrackingProject.dto.response.ProductReviewResponse;
import com.example.miniTrackingProject.entity.ProductReviewsEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.ReviewMapper;
import com.example.miniTrackingProject.repository.ProductRepository;
import com.example.miniTrackingProject.repository.ProductReviewRepository;
import com.example.miniTrackingProject.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    private final SecurityHelper securityHelper;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public String create(ProductReviewRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        validateBuyerRole(user);

        ProductsEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductReviewsEntity review = productReviewRepository
                .findByBuyerIdAndProductId(user.getId(), request.getProductId())
                .orElse(new ProductReviewsEntity());

        if (review.getId() != null && Boolean.FALSE.equals(review.getIsDelete())) {
            throw new JavaBuilderException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        review.setProduct(product);
        review.setBuyer(user);
        review.setRating(request.getRating());
        review.setContent(request.getContent());
        review.setIsAnonymous(request.getIsAnonymous());
        review.setIsDelete(false);

        if (review.getId() == null) {
            review.setCreatedAt(LocalDateTime.now());
        } else {
            review.setUpdatedAt(LocalDateTime.now());
        }

        productReviewRepository.save(review);
        return "Đánh giá thành công";
    }

    @Override
    public List<ProductReviewResponse> getReviewByProduct(Long productId, int limit, int offset) {
        return productReviewRepository.findReviewsWithOffset(productId, limit, offset)
                .stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductReviewResponse updateProductReview(Long id, ProductReviewRequest request) {
        UserEntity user = securityHelper.getCurrentUser();
        ProductReviewsEntity review = findAndValidateOwner(id, user);

        review.setRating(request.getRating());
        review.setContent(request.getContent());
        review.setIsAnonymous(request.getIsAnonymous());
        review.setUpdatedAt(LocalDateTime.now());

        return reviewMapper.toResponse(productReviewRepository.save(review));
    }

    @Override
    @Transactional
    public String deleteReview(Long id) {
        UserEntity user = securityHelper.getCurrentUser();
        ProductReviewsEntity review = findAndValidateOwner(id, user);

        review.setIsDelete(true);
        review.setUpdatedAt(LocalDateTime.now());
        productReviewRepository.save(review);

        return "Xóa đánh giá thành công";
    }


    private void validateBuyerRole(UserEntity user) {
        if (!RoleEnum.BUYER.equals(user.getRole())) {
            throw new JavaBuilderException(ErrorCode.INVALID_STATUS);
        }
    }

    private ProductReviewsEntity findAndValidateOwner(Long reviewId, UserEntity user) {
        ProductReviewsEntity review = productReviewRepository.findById(reviewId)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.NOT_FOUND));

        if (!user.getId().equals(review.getBuyer().getId())) {
            throw new JavaBuilderException(ErrorCode.ACCESS_DENIED);
        }
        return review;
    }
}