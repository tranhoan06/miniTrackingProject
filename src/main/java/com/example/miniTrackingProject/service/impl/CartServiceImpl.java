package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.dto.request.CartRequest;
import com.example.miniTrackingProject.dto.response.CartResponse;
import com.example.miniTrackingProject.entity.CartEntity;
import com.example.miniTrackingProject.entity.CartItemsEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.repository.CartItemRepository;
import com.example.miniTrackingProject.repository.CartRepository;
import com.example.miniTrackingProject.repository.ProductRepository;
import com.example.miniTrackingProject.repository.UserRepository;
import com.example.miniTrackingProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void addCart(CartRequest request) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        ProductsEntity productsEntity = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        CartEntity cartEntity = cartRepository.findByUser(userEntity.getId())
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(userEntity);
                    return cartRepository.save(newCart);
                });

        Optional<CartItemsEntity> cartItemOptional =
                cartItemRepository.findByCartAndProduct(cartEntity, productsEntity);

        if(cartItemOptional.isPresent()) {
            CartItemsEntity cartItemsEntity = cartItemOptional.get();
            cartItemsEntity.setQuantity(cartItemsEntity.getQuantity() + request.getQuantity());
            cartItemsEntity.setTotalAmount(
                    cartItemsEntity.getPrice().multiply(BigDecimal.valueOf(cartItemsEntity.getQuantity()))
            );

            cartItemRepository.save(cartItemsEntity);
        } else {
            CartItemsEntity cartItemsEntity = new CartItemsEntity();
            cartItemsEntity.setCart(cartEntity);
            cartItemsEntity.setProduct(productsEntity);
            cartItemsEntity.setQuantity(request.getQuantity());
            cartItemsEntity.setPrice(productsEntity.getPrice());
            cartItemsEntity.setTotalAmount(
                    productsEntity.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))
            );

            cartItemRepository.save(cartItemsEntity);
        }
    }
}
