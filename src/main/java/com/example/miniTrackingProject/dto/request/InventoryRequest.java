package com.example.miniTrackingProject.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryRequest implements Serializable {
    private Long quantityInStock;

    private Long reservedQuantity;
}
