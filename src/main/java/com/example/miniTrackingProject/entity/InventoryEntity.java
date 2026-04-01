package com.example.miniTrackingProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductsEntity product;

    @Column(name = "quantity_in_stock", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long quantityInStock;

    @Column(name = "reserved_quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Long reservedQuantity;

    @Column(name = "isDelete", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDelete;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
