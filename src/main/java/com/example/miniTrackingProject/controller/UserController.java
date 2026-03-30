package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.request.UserRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.UserResponse;
import com.example.miniTrackingProject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/v1/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<UserResponse>> createUser(
            @Valid @RequestBody UserRequest request,
            HttpServletRequest httpRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(
                        userService.createUser(request)
                ));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@PathVariable Long id,
                                                                 @RequestBody UserRequest request,
                                                                 HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        userService.updateUser(id, request)
                ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable Long id,
                                                                  HttpServletRequest httpRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        userService.getUserById(id)
                ));
    }
}
