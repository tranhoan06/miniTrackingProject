package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.CartRequest;
import com.example.miniTrackingProject.dto.response.CartResponse;

public interface CartService {

    void addCart(CartRequest request);

    CartResponse getAllCartByUser();

    void removeFromCart(Long id);
}
