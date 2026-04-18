package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.OrderStatusLogResponse;
import com.example.miniTrackingProject.entity.OrderStatusLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStatusLogMapper {
    @Mapping(source = "order.id", target = "orderId")
    OrderStatusLogResponse toResponse(OrderStatusLogEntity entity);

    List<OrderStatusLogResponse> toResponse(List<OrderStatusLogEntity> entities);
}
