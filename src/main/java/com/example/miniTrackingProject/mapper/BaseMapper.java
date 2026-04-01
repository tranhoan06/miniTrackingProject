package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.request.InventoryRequest;
import com.example.miniTrackingProject.dto.request.ProductImageRequest;
import com.example.miniTrackingProject.dto.response.CategoryResponse;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import com.example.miniTrackingProject.entity.CategoriesEntity;
import com.example.miniTrackingProject.entity.InventoryEntity;
import com.example.miniTrackingProject.entity.ProductImagesEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toCategoryResponse(CategoriesEntity entity);

    @Mapping(source = "seller.id", target = "seller")
    @Mapping(source = "category.id", target = "category")
    ProductResponse toProductResponse(ProductsEntity entity);

    List<ProductImagesEntity> toImageEntities(List<ProductImageRequest> requests);
    List<InventoryEntity> toInventoryEntities(List<InventoryRequest> requests);
}
