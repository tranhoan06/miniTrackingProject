package com.example.miniTrackingProject.entity;

import com.example.miniTrackingProject.common.StatusProduct;
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

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoriesEntity category;

    @Column(name = "product_name", length = 255, nullable = false)
    private String productName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "original_price", precision = 15, scale = 2)
    private BigDecimal originalPrice = BigDecimal.ZERO;

    @Column(name = "weight_gram", precision = 10, scale = 2)
    private BigDecimal weightGram = BigDecimal.ZERO;

    @Column(name = "length_cm", precision = 6, scale = 2)
    private BigDecimal lengthCm = BigDecimal.ZERO;

    @Column(name = "width_cm", precision = 6, scale = 2)
    private BigDecimal widthCm = BigDecimal.ZERO;

    @Column(name = "height_cm", precision = 6, scale = 2)
    private BigDecimal heightCm = BigDecimal.ZERO;

    private String sku;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProduct status;

    @Column(name = "is_Delete", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDelete;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImagesEntity> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<InventoryEntity> inventories = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
