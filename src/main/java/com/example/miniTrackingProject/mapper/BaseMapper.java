package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.CategoryResponse;
import com.example.miniTrackingProject.dto.response.UserResponse;
import com.example.miniTrackingProject.entity.CategoriesEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toCategoryResponse(CategoriesEntity entity);
}
