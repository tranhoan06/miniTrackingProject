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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
