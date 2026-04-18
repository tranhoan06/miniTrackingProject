package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;
import com.example.miniTrackingProject.entity.ShippingProviderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ShippingProviderMapper {

    ShippingProviderResponse toResponse(ShippingProviderEntity entity);
    List<ShippingProviderResponse> toResponse(List<ShippingProviderEntity> entity);
}
