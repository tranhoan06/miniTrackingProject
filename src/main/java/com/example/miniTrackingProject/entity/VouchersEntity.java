package com.example.miniTrackingProject.entity;

import com.example.miniTrackingProject.common.DiscountType;
import com.example.miniTrackingProject.common.StatusVoucher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VouchersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String code;

    private String description;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserEntity seller;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue = BigDecimal.ZERO;

    @Column(name = "max_discount")
    private BigDecimal maxDiscount;

    @Column(name = "min_order_amount")
    private BigDecimal minOrderAmount;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "used_count")
    private Integer usedCount = 0;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVoucher status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusVoucher.ACTIVE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
