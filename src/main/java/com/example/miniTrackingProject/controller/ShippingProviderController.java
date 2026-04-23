package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.DeliveredOrderRequest;
import com.example.miniTrackingProject.dto.request.ShippingOrderRequest;
import com.example.miniTrackingProject.dto.request.ShippingProviderRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.ShippingProviderResponse;
import com.example.miniTrackingProject.service.ShippingProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/api/shipping-provider")
@RequiredArgsConstructor
public class ShippingProviderController {

    private final ShippingProviderService shippingProviderService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<ShippingProviderResponse>>> getAll () {
        List<ShippingProviderResponse> response = shippingProviderService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    @GetMapping("/create")
    public ResponseEntity<BaseResponse<ShippingProviderResponse>> create (@Valid @RequestBody ShippingProviderRequest request) {
        ShippingProviderResponse response = shippingProviderService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    @PostMapping("update/{id}")
    public ResponseEntity<BaseResponse<ShippingProviderResponse>> update (@PathVariable Long id, @Valid @RequestBody ShippingProviderRequest request) {
        ShippingProviderResponse response = shippingProviderService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ShippingProviderResponse>> getById (@PathVariable Long id) {

        ShippingProviderResponse response = shippingProviderService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    // api vận chuyển
    @PostMapping("/shippingOrder")
    public ResponseEntity<BaseResponse<String>> shippingOrder (@Valid  @RequestBody ShippingOrderRequest request) {

        String response = shippingProviderService.shippingOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

    // api giao hàng thành công
    @PostMapping("/delivered")
    @PreAuthorize("hasAnyRole('ROLE_SHIPPER')")
    public ResponseEntity<BaseResponse<String>> deliveredOrder (@Valid  @RequestBody DeliveredOrderRequest request) {
        String response = shippingProviderService.deliveredOrder(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(
                        response
                ));
    }

}
