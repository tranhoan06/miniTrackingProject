package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.ErrorCode;
import com.example.miniTrackingProject.common.StatusProduct;
import com.example.miniTrackingProject.dto.request.FilterProductRequest;
import com.example.miniTrackingProject.dto.request.InventoryRequest;
import com.example.miniTrackingProject.dto.request.ProductImageRequest;
import com.example.miniTrackingProject.dto.request.ProductRequest;
import com.example.miniTrackingProject.dto.response.ProductOverviewResponse;
import com.example.miniTrackingProject.dto.response.ProductResponse;
import com.example.miniTrackingProject.entity.*;
import com.example.miniTrackingProject.exception.JavaBuilderException;
import com.example.miniTrackingProject.mapper.BaseMapper;
import com.example.miniTrackingProject.repository.*;
import com.example.miniTrackingProject.service.ProductService;
import com.example.miniTrackingProject.service.spec.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
    public Page<ProductResponse> getAll(Integer pageSize, Integer pageNumber, FilterProductRequest request) {
        // tạo một điều kiện ban đầu luôn đúng để có thể .and() thêm các filter sau đó
        Specification<ProductsEntity> specification =
                (root, query, cb) -> cb.isFalse(root.get("isDelete"));

        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            specification = specification.and(ProductSpecification.likeKeyword(request.getKeyword()));
        }

        if (request.getMinPrice() != null) {
            specification = specification.and(ProductSpecification.minPrice(request.getMinPrice()));
        }

        if (request.getMaxPrice() != null) {
            specification = specification.and(ProductSpecification.maxPrice(request.getMaxPrice()));
        }

        if (request.getStatus() != null) {
            specification = specification.and(ProductSpecification.filterStatus(request.getStatus()));
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").ascending());
//        Page<ProductsEntity> entityPage = productRepository.findByIsDeleteFalse(specification, pageable);
//        Page<ProductsEntity> entityPage = productRepository.findAllActive(pageable);
        Page<ProductsEntity> entityPage = productRepository.findAll(specification, pageable);
        return entityPage.map(baseMapper::toProductResponse);
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserEntity userEntity = userRepository.findByUsername(username)
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
            productsEntity.setImages(buildImages(request.getImages(), productsEntity));
        }

        if (request.getInventories() != null && !request.getInventories().isEmpty()) {
            productsEntity.setInventories(buildInventories(request.getInventories(), productsEntity));
        }

        productRepository.save(productsEntity);
        return baseMapper.toProductResponse(productsEntity);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        ProductsEntity productsEntity = productRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        CategoriesEntity categories = categoryRepository.findById(request.getCategory())
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.CATEGORYID_NOT_FOUND));

        productsEntity.setProductName(request.getProductName());
        productsEntity.setCategory(categories);
        productsEntity.setPrice(request.getPrice());
        productsEntity.setOriginalPrice(request.getOriginalPrice());
        productsEntity.setWeightGram(request.getWeightGram());
        productsEntity.setLengthCm(request.getLengthCm());
        productsEntity.setWidthCm(request.getWidthCm());
        productsEntity.setHeightCm(request.getHeightCm());
        productsEntity.setStatus(request.getStatus());
        productsEntity.setUpdatedAt(LocalDateTime.now());

        if (productsEntity.getImages() != null) {
            productsEntity.getImages().forEach(img -> img.setIsDelete(true));
            productImageRepository.saveAll(productsEntity.getImages());
        }

        if (productsEntity.getInventories() != null) {
            validateNoReserved(productsEntity.getInventories());

            productsEntity.getInventories().forEach(inv -> inv.setIsDelete(true));
            inventoryRepository.saveAll(productsEntity.getInventories());
        }

        if (request.getImages() != null && !request.getImages().isEmpty()) {
            productsEntity.setImages(buildImages(request.getImages(), productsEntity));
        }

        if (request.getInventories() != null && !request.getInventories().isEmpty()) {
            productsEntity.setInventories(buildInventories(request.getInventories(), productsEntity));
        }

        productRepository.save(productsEntity);
        return baseMapper.toProductResponse(productsEntity);
    }

    @Override
    public ProductResponse getProductDetail(Long id) {
        ProductsEntity productsEntity = productRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));
        List<ProductImagesEntity> productImagesEntityList = productsEntity.getImages().stream()
                .filter(img -> !img.getIsDelete())
                .collect(Collectors.toList());

        List<InventoryEntity> inventoryEntityList = productsEntity.getInventories().stream()
                .filter(img -> !img.getIsDelete())
                .collect(Collectors.toList());

        productsEntity.setImages(productImagesEntityList);
        productsEntity.setInventories(inventoryEntityList);
        return baseMapper.toProductResponse(productsEntity);
    }

    @Override
    public ProductOverviewResponse getProductOverview() {
        ProductOverviewResponse productOverviewResponse = new ProductOverviewResponse();
        Long count = productRepository.countProduct();
        BigDecimal totalPriceProduct = productRepository.getTotalPriceProuct();
        Long otalAlmostOutOfStock = productRepository.getTotalAlmostOutOfStock();

        productOverviewResponse.setTotalProduct(count);
        productOverviewResponse.setTotalPriceProuct(totalPriceProduct);
        productOverviewResponse.setTotalAlmostOutOfStock(otalAlmostOutOfStock);
        return productOverviewResponse;
    }

    @Override
    public void deleteProduct(Long id) {
        ProductsEntity productsEntity = productRepository.findById(id)
                .orElseThrow(() -> new JavaBuilderException(ErrorCode.PRODUCT_NOT_FOUND));

        productsEntity.setIsDelete(true);
        productsEntity.setUpdatedAt(LocalDateTime.now());
        productRepository.save(productsEntity);
    }

    private List<ProductImagesEntity> buildImages(List<ProductImageRequest> requests, ProductsEntity product) {

        List<ProductImageRequest> sortedRequests = requests.stream()
                .sorted(Comparator.comparing(ProductImageRequest::getSortOrder))
                .collect(Collectors.toList());

        List<ProductImagesEntity> result = new ArrayList<>();

        boolean hasThumbnail = false;
        Long index = 1L;

        for (ProductImageRequest img : sortedRequests) {

            ProductImagesEntity image = new ProductImagesEntity();
            image.setImageUrl(img.getImageUrl());

            if (Boolean.TRUE.equals(img.getIsThumbnail()) && !hasThumbnail) {
                image.setIsThumbnail(true);
                hasThumbnail = true;
            } else {
                image.setIsThumbnail(false);
            }

            image.setSortOrder(index++);

            image.setIsDelete(false);
            image.setProduct(product);

            result.add(image);
        }

        if (!hasThumbnail && !result.isEmpty()) {
            result.get(0).setIsThumbnail(true);
        }

        return result;
    }

    private List<InventoryEntity> buildInventories(List<InventoryRequest> requests, ProductsEntity product) {
        return requests.stream()
                .map(inv -> {
                    InventoryEntity inventory = new InventoryEntity();
                    inventory.setQuantityInStock(inv.getQuantityInStock());
                    inventory.setReservedQuantity(0L);
                    inventory.setProduct(product);
                    inventory.setIsDelete(false);
                    return inventory;
                }).collect(Collectors.toList());
    }

    private void validateNoReserved(List<InventoryEntity> inventories) {
        boolean hasReserved = inventories.stream()
                .anyMatch(inv -> inv.getReservedQuantity() != null
                        && inv.getReservedQuantity() > 0);

        if (hasReserved) {
            throw new JavaBuilderException(ErrorCode.INVENTORY_HAS_RESERVED_QUANTITY);
        }
    }
}
