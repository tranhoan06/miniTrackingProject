package com.example.miniTrackingProject.dto.response;

import com.example.miniTrackingProject.common.StatusProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCartResponse implements Serializable {
    private Long id;

    private Long seller;

    private Long category;

    private String productName;

    private String description;

    private BigDecimal price;

    private BigDecimal originalPrice;

    private String sku;

    private StatusProduct status;

    private List<ProductImagesResponse> images = new ArrayList<>();

    private List<InventoryResponse> inventories = new ArrayList<>();
}
