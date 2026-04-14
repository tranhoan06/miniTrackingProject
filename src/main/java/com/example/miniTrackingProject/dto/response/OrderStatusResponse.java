package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponse {
    private String action;
    private Integer totalRequested;
    private Integer totalSuccess;
    private List<OrderResultDetailResponse> details;
}
