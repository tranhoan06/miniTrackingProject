package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.CategoryRequest;
import com.example.miniTrackingProject.dto.response.CategoryResponse;
import com.example.miniTrackingProject.entity.CategoriesEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.BaseMapper;
import com.example.miniTrackingProject.repository.CategoryRepository;
import com.example.miniTrackingProject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BaseMapper baseMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        CategoriesEntity parent = null;
        if (request.getParentId() != null) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));
        }

        boolean exists;
        if (parent == null) {
            exists = categoryRepository
                    .existsByCategoryNameAndParentIsNull(request.getCategoryName());
        } else {
            exists = categoryRepository
                    .existsByCategoryNameAndParent_Id(request.getCategoryName(), parent.getId());
        }

        if (exists) {
            throw new JavaBuilderException(ErrorCode.CATEGORY_IS_DUPLICATED);
        }

        CategoriesEntity category = new CategoriesEntity();
        category.setCategoryName(request.getCategoryName());
        category.setParent(parent);
        category.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        category.setIsDelete(false);
        category.setCreatedAt(LocalDateTime.now());

        categoryRepository.save(category);

        return baseMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        CategoriesEntity categories = categoryRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));
        return baseMapper.toCategoryResponse(categories);
    }
}
