package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.StatusProduct;
import com.example.miniTrackingProject.entity.CategoriesEntity;
import com.example.miniTrackingProject.entity.InventoryEntity;
import com.example.miniTrackingProject.entity.ProductImagesEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private Long id;

    private Long seller;

    private Long category;

    private String productName;

    private String description;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private BigDecimal weightGram;

    private BigDecimal lengthCm;

    private BigDecimal widthCm;

    private BigDecimal heightCm;

    private String sku;

    private StatusProduct status;

    private List<ProductImagesResponse> images = new ArrayList<>();

    private List<InventoryResponse> inventories = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
