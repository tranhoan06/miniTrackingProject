package com.example.miniTrackingProject.dto.request;

import com.example.miniTrackingProject.common.StatusProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {
    @NotNull
    private Long seller;

    @NotNull
    private Long category;

    @NotEmpty(message = "Không được để trống")
    private String productName;

    private BigDecimal price;

    @NotNull
    private BigDecimal originalPrice;

    @NotNull
    private BigDecimal weightGram;

    @NotNull
    private BigDecimal lengthCm;

    @NotNull
    private BigDecimal widthCm;

    @NotNull
    private BigDecimal heightCm;

    private StatusProduct status;

    private List<ProductImageRequest> images;
    private List<InventoryRequest> inventories;
}
