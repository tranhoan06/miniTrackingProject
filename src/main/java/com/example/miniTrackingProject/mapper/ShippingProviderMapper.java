package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;
import com.example.miniTrackingProject.entity.ShippingProviderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ShippingProviderMapper {

    ShippingProviderResponse toResponse(ShippingProviderEntity entity);

}
