package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.CategoryRequest;
import com.example.miniTrackingProject.dto.response.CategoryResponse;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse getCategoryById(Long id);
}
