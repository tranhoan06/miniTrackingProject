package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.OrderItemsResponse;
import com.example.miniTrackingProject.entity.OrderItemsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "seller.id", target = "sellerId")
    OrderItemsResponse toOrderItemsResponse (OrderItemsEntity entity);
}
