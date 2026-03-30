package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.UserRequest;
import com.example.miniTrackingProject.dto.response.UserResponse;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.UserMapper;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(UserRequest request) {
        log.info("Checking username: {}", request.getUsername());
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new JavaBuilderException(ErrorCode.USERNAME_IS_DUPLICATED);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setFullname(request.getFullname());
        userEntity.setRole(request.getRole());
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setIsDelete(false);
        userRepository.save(userEntity);
        return userMapper.toResponse(userEntity);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));
        userEntity.setFullname(request.getFullname());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setRole(request.getRole());
        userRepository.save(userEntity);
        return userMapper.toResponse(userEntity);
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toResponse(userEntity);
    }
}
