package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.CartResponse;
import com.example.miniTrackingProject.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = { ProductCartMapper.class, CartItemMapper.class })
public interface CartMapper {

    @Mapping(source = "user.id", target = "user")
    CartResponse toCartResponse(CartEntity entity);
}
