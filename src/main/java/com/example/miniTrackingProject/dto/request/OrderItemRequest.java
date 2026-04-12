package com.example.miniTrackingProject.dto.request;

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
public class OrderItemRequest implements Serializable {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;
}
