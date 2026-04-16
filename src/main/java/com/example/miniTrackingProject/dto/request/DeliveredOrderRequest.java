package com.example.miniTrackingProject.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveredOrderRequest implements Serializable {
    @NotNull
    private String trackingCode;

    @NotNull
    private String note;
}
