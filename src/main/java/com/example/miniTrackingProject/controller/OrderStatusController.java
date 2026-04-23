package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.OrderStatusLogRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.OrderStatusLogResponse;
import com.example.miniTrackingProject.service.OrderStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/api/log")
@RequiredArgsConstructor
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    // api ghi hành trình
    @PostMapping("")
    public ResponseEntity<BaseResponse<List<OrderStatusLogResponse>>> getLog(@Valid @RequestBody OrderStatusLogRequest request) {
        List<OrderStatusLogResponse> response = orderStatusService.getLog(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));

    }
}
