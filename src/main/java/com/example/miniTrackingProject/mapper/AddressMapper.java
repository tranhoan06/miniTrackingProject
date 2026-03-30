package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.AddressResponse;
import com.example.miniTrackingProject.entity.AddresesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    // Map entity → response
    // user.id trong entity → field user trong response
    // src: lấy dữ liệu từ đâu, target: đặt dữ liệu vào đâu
    @Mapping(source = "user.id", target = "user")
    AddressResponse toResponse(AddresesEntity entity);
}