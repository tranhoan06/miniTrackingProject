package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {
    @NotEmpty(message = "Không được để trống")
    private String categoryName;

    private Long parentId;

    private Boolean isActive;

}
