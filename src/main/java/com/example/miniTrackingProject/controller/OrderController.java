package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.OrderRequest;
import com.example.miniTrackingProject.dto.request.PreviewOrderRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.OrderResponse;
import com.example.miniTrackingProject.dto.response.PreviewOrderResponse;
import com.example.miniTrackingProject.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/v1/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/preview")
    public ResponseEntity<BaseResponse<PreviewOrderResponse>> previewOrder(@RequestBody PreviewOrderRequest request) {
        PreviewOrderResponse response = orderService.previewOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(@Valid @RequestBody OrderRequest request) {
        String response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<BaseResponse<String>> getOrderBySeller(@PathVariable Long sellerId) {
        String response = orderService.getOrderBySeller(sellerId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

}
