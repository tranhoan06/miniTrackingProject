package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.FilterProductRequest;
import com.example.miniTrackingProject.dto.request.ProductRequest;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAll(Integer pageSize, Integer pageNumber);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    ProductResponse getProductDetail(Long id);

    Page<ProductResponse> filterProduct(FilterProductRequest request);
}
