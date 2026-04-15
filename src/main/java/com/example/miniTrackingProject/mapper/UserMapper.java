package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.UserResponse;
import com.example.miniTrackingProject.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = ShippingProviderMapper.class)
public interface UserMapper {
    @Mapping(target = "shippingProviderId", source = "shippingProvider.id")
    UserResponse toResponse(UserEntity entity);
}
