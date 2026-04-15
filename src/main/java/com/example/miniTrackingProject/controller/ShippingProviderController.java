package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.ShippingOrderRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;
import com.example.miniTrackingProject.service.ShippingProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/v1/api/shipping-provider")
@RequiredArgsConstructor
public class ShippingProviderController {

    private final ShippingProviderService shippingProviderService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ShippingProviderResponse>> getAllById (@PathVariable Long id) {

        ShippingProviderResponse response = shippingProviderService.getAllById(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    @PostMapping("/shippingOrder")
    public ResponseEntity<BaseResponse<String>> shippingOrder (@Valid  @RequestBody ShippingOrderRequest request) {

        String response = shippingProviderService.shippingOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

}
