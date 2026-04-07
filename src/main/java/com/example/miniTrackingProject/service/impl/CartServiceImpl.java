package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.common.SecurityHelper;
import com.example.miniTrackingProject.dto.request.CartRequest;
import com.example.miniTrackingProject.dto.response.CartResponse;
import com.example.miniTrackingProject.entity.CartEntity;
import com.example.miniTrackingProject.entity.CartItemsEntity;
import com.example.miniTrackingProject.entity.ProductsEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.CartMapper;
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
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final SecurityHelper securityHelper;

    @Override
    public void addCart(CartRequest request) {
        UserEntity userEntity = securityHelper.getCurrentUser();

        ProductsEntity productsEntity = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        CartEntity cartEntity = cartRepository.findByUser(userEntity)
                .orElseGet(() -> {
                    CartEntity newCart = new CartEntity();
                    newCart.setUser(userEntity);
                    newCart.setCreatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });

        Optional<CartItemsEntity> cartItemOptional =
                cartItemRepository.findByCartAndProduct(cartEntity, productsEntity);

        if(cartItemOptional.isPresent() && !cartItemOptional.get().getIsDelete()) {
            CartItemsEntity cartItemsEntity = cartItemOptional.get();
            cartItemsEntity.setQuantity(cartItemsEntity.getQuantity() + request.getQuantity());
            cartItemsEntity.setCreatedAt(LocalDateTime.now());

            cartItemRepository.save(cartItemsEntity);
        } else {
            CartItemsEntity cartItemsEntity = new CartItemsEntity();
            cartItemsEntity.setCart(cartEntity);
            cartItemsEntity.setProduct(productsEntity);
            cartItemsEntity.setQuantity(request.getQuantity());
            cartItemsEntity.setIsDelete(false);
            cartItemsEntity.setCreatedAt(LocalDateTime.now());
            cartItemRepository.save(cartItemsEntity);
        }
    }

    @Override
    public CartResponse getAllCartByUser() {
        UserEntity userEntity = securityHelper.getCurrentUser();

        CartEntity cartEntity = cartRepository.findByUser(userEntity)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CART_NOT_FOUND));
        return cartMapper.toCartResponse(cartEntity);
    }

    @Override
    public void removeFromCart(Long id) {
        UserEntity userEntity = securityHelper.getCurrentUser();

        CartEntity cartEntity = cartRepository.findByUser(userEntity)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CART_NOT_FOUND));

        ProductsEntity productsEntity = productRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        Optional<CartItemsEntity> cartItemsEntity = cartItemRepository.findByCartAndProduct(cartEntity, productsEntity);
        if(cartItemsEntity.isPresent()) {
            CartItemsEntity cartItems = cartItemsEntity.get();
            cartItems.setIsDelete(true);
            cartItems.setUpdatedAt(LocalDateTime.now());
            cartItemRepository.save(cartItems);
        }
    }
}
