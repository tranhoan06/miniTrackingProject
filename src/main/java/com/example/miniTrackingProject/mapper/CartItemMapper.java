package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.CartsItemResponse;
import com.example.miniTrackingProject.entity.CartItemsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring",
    uses = { ProductCartMapper.class}
)
public interface CartItemMapper {
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "totalAmount", expression = "java(calculateTotal(entity))")
    CartsItemResponse toCartsItemResponse(CartItemsEntity entity);

    default BigDecimal calculateTotal(CartItemsEntity entity) {
        if (entity.getProduct() == null || entity.getProduct().getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return entity.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(entity.getQuantity()));
    }
}
