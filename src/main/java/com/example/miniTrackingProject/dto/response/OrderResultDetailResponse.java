package com.example.miniTrackingProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResultDetailResponse implements Serializable {
    private Long orderId;
    private String status;      // Trạng thái mới (ví dụ: "CONFIRMED")
    private String message;     // Thông báo riêng: "Thành công" hoặc "Lỗi: Không tìm thấy"
    private Boolean isSuccess;
}
