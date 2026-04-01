package com.example.miniTrackingProject.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImagesResponse implements Serializable {
    private String imageUrl;

    private Boolean isThumbnail;

    private Long sortOrder;
}
