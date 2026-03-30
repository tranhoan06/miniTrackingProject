package com.example.miniTrackingProject.mapper;

import com.example.miniTrackingProject.dto.response.UserResponse;
import com.example.miniTrackingProject.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse(UserEntity entity);
}
