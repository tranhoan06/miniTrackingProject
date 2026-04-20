package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.repository.ProductReviewRepository;
import com.example.miniTrackingProject.service.ProductReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
}
