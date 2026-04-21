package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.ProductRequest;
import com.example.miniTrackingProject.dto.request.ProductReviewRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import com.example.miniTrackingProject.dto.response.ProductReviewResponse;
import com.example.miniTrackingProject.service.ProductReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/api/product-review")
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    @PostMapping("create")
    public ResponseEntity<BaseResponse<String>> create(@Valid @RequestBody ProductReviewRequest request) {
        String productResponse = productReviewService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(productResponse));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<BaseResponse<List<ProductReviewResponse>>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int offset, // Số lượng đã hiển thị
            @RequestParam(defaultValue = "3") int limit) { // Số lượng muốn lấy thêm

        return ResponseEntity.ok(BaseResponseFactory.success(
                productReviewService.getReviewByProduct(productId, limit, offset)
        ));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<BaseResponse<ProductReviewResponse>> updateProductReview(@PathVariable Long id, @Valid @RequestBody ProductReviewRequest request) {
        ProductReviewResponse response = productReviewService.updateProductReview(id, request);
        return ResponseEntity.ok(BaseResponseFactory.success(response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteReview(@PathVariable Long id) {
        String response = productReviewService.deleteReview(id);
        return ResponseEntity.ok(BaseResponseFactory.success(response));
    }
}
