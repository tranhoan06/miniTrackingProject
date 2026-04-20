package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/api/product-review")
public class ProductReviewController {
    private final ProductReviewService productReviewService;
}
