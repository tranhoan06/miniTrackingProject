package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.common.StatusProduct;
import com.example.miniTrackingProject.dto.request.ProductRequest;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import com.example.miniTrackingProject.entity.*;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.BaseMapper;
import com.example.miniTrackingProject.repository.*;
import com.example.miniTrackingProject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BaseMapper baseMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public Page<ProductResponse> getAll(Integer pageSize, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").ascending());

        Page<ProductsEntity> entityPage = productRepository.findAll(pageable);
        return entityPage.map(baseMapper::toProductResponse);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        UserEntity userEntity = userRepository.findById(request.getSeller())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.USER_NOT_FOUND));

        CategoriesEntity categories = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));
        ProductsEntity productsEntity = new ProductsEntity();

        productsEntity.setProductName(request.getProductName());
        productsEntity.setSeller(userEntity);
        productsEntity.setCategory(categories);
        productsEntity.setPrice(request.getPrice());
        productsEntity.setOriginalPrice(request.getOriginalPrice());
        productsEntity.setWeightGram(request.getWeightGram());
        productsEntity.setLengthCm(request.getLengthCm());
        productsEntity.setWidthCm(request.getWidthCm());
        productsEntity.setHeightCm(request.getHeightCm());
        productsEntity.setIsDelete(false);
        productsEntity.setStatus(StatusProduct.ACTIVE);
        productsEntity.setCreatedAt(LocalDateTime.now());

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            List<ProductImagesEntity> images = request.getImages().stream()
                    .map(img -> {
                        ProductImagesEntity image = new ProductImagesEntity();
                        image.setImageUrl(img.getImageUrl());
                        image.setIsThumbnail(img.getIsThumbnail());
                        image.setSortOrder(img.getSortOrder());
                        image.setIsDelete(false);        // ✅ mặc định false
                        image.setProduct(productsEntity);  // ✅ gắn product
                        return image;
                    }).collect(Collectors.toList());
            productsEntity.setImages(images);
        }

        // 3. Xử lý inventories
        if (request.getInventories() != null && !request.getInventories().isEmpty()) {
            List<InventoryEntity> inventories = request.getInventories().stream()
                    .map(inv -> {
                        InventoryEntity inventory = new InventoryEntity();
                        inventory.setQuantityInStock(inv.getQuantityInStock());
                        inventory.setReservedQuantity(0L); // ✅ mặc định 0
                        inventory.setProduct(productsEntity); // ✅ gắn product
                        return inventory;
                    }).collect(Collectors.toList());
            productsEntity.setInventories(inventories);
        }

        productRepository.save(productsEntity);
        return baseMapper.toProductResponse(productsEntity);
    }
}
