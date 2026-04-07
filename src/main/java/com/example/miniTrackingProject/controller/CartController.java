package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.CartRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.CartResponse;
import com.example.miniTrackingProject.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/api/cart")
public class CartController implements Serializable {
    private final CartService cartService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<CartResponse>> getAllCartByUser() {
        CartResponse response = cartService.getAllCartByUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success(response));
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<String>> addCart(@Valid @RequestBody CartRequest request) {
        cartService.addCart(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success("Đã thêm vào giỏ hàng"));
    }

    @DeleteMapping("remove-from-cart/{id}")
    public ResponseEntity<BaseResponse<String>> removeFromCart(@PathVariable Long id) {
        cartService.removeFromCart(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponseFactory.success("Đã xóa khỏi giỏ hàng"));
    }

}
