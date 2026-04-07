package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.ProductCartResponse;
import com.example.miniTrackingProject.entity.ProductsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductCartMapper {

    @Mapping(source = "seller.id", target = "seller")
    @Mapping(source = "category.id", target = "category")
    ProductCartResponse toProductResponse(ProductsEntity entity);

}
