package com.example.miniTrackingProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_thumbnail", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isThumbnail;

    @Column(name = "sort_order", columnDefinition = "BIGINT DEFAULT 0")
    private Long sortOrder;

    @Column(name = "isDelete", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDelete;
}
