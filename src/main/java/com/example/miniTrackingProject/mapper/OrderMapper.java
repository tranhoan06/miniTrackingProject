package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.entity.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = {OrderItemsMapper.class})
public interface OrderMapper {

    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "seller.id", target = "sellerId")
    OrderResponse toOrderResponse(OrdersEntity entity);
}
