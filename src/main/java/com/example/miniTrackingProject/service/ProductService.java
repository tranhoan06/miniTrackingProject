package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.ProductRequest;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductResponse> getAll(Integer pageSize, Integer pageNumber);

    ProductResponse createProduct(ProductRequest request);
}
