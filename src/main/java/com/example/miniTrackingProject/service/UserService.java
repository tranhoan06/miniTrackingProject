package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.UserRequest;
import com.example.miniTrackingProject.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    UserResponse getUserById(Long id);
}
