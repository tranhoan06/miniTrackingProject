package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.ProductReviewRequest;
import com.example.miniTrackingProject.dto.response.ProductReviewResponse;

import java.util.List;

public interface ProductReviewService {
    String create(ProductReviewRequest request);

    List<ProductReviewResponse> getReviewByProduct(Long productId, int pageNumber, int pageSize);

    ProductReviewResponse updateProductReview(Long id, ProductReviewRequest request);

    String deleteReview(Long id);
}
