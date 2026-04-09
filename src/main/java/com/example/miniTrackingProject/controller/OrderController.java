package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.dto.response.ProductOverviewResponse;
import com.example.miniTrackingProject.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private ResponseEntity<BaseResponse<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return null;
    }

}
