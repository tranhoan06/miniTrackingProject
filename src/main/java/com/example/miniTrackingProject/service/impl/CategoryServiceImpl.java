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
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BaseMapper baseMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        CategoriesEntity parent = getParent(request.getParentId());

        validateDuplicate(request.getCategoryName(), parent, null);

        CategoriesEntity category = new CategoriesEntity();
        category.setCategoryName(request.getCategoryName());
        category.setParent(parent);
        category.setIsActive(Boolean.TRUE.equals(request.getIsActive()));
        category.setIsDelete(false);
        category.setCreatedAt(LocalDateTime.now());

        return baseMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        CategoriesEntity categories = categoryRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));
        return baseMapper.toCategoryResponse(categories);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        CategoriesEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));

        CategoriesEntity parent = getParent(request.getParentId());

        validateDuplicate(request.getCategoryName(), parent, id);

        category.setCategoryName(request.getCategoryName());
        category.setParent(parent);
        category.setIsActive(request.getIsActive());
        category.setUpdatedAt(LocalDateTime.now());

        return baseMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        CategoriesEntity categories = categoryRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));

        List<CategoriesEntity> categoriesEntityList = categories.getChildren();
        for (CategoriesEntity child: categoriesEntityList) {
            child.setParent(null);
        }
        categories.setUpdatedAt(LocalDateTime.now());
        categories.setIsDelete(true);
        categoryRepository.save(categories);
    }

    private CategoriesEntity getParent(Long parentId) {
        if (parentId == null) return null;

        return categoryRepository.findById(parentId)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));
    }

    private void validateDuplicate(String name, CategoriesEntity parent, Long currentId) {

        boolean exists;

        if (parent == null) {
            exists = (currentId == null)
                    ? categoryRepository.existsByCategoryNameAndParentIsNull(name)
                    : categoryRepository.existsByCategoryNameAndParentIsNullAndIdNot(name, currentId);
        } else {
            exists = (currentId == null)
                    ? categoryRepository.existsByCategoryNameAndParent_Id(name, parent.getId())
                    : categoryRepository.existsByCategoryNameAndParent_IdAndIdNot(name, parent.getId(), currentId);
        }

        if (exists) {
            throw new JavaBuilderException(ErrorCode.CATEGORY_IS_DUPLICATED);
        }
    }
}
