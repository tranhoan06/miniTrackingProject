package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequest implements Serializable {
    @NotNull
    private String imageUrl;

    @NotNull
    private Boolean isThumbnail;

    @NotNull
    private Long sortOrder;
}
