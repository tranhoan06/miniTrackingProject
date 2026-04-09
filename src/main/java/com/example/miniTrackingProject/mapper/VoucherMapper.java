package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.VoucherResponse;
import com.example.miniTrackingProject.entity.VouchersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    @Mapping(source = "seller.id", target = "seller")
    VoucherResponse toVoucherResponse(VouchersEntity entity);
}
