package com.example.miniTrackingProject.controller;

import com.example.miniTrackingProject.dto.request.VoucherRequest;
import com.example.miniTrackingProject.dto.response.BaseResponse;
import com.example.miniTrackingProject.dto.response.BaseResponseFactory;
import com.example.miniTrackingProject.dto.response.UserResponse;
import com.example.miniTrackingProject.dto.response.VoucherResponse;
import com.example.miniTrackingProject.service.VoucherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/v1/api/voucher")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<VoucherResponse>> createVoucher(@Valid @RequestBody VoucherRequest request) {
        VoucherResponse voucherResponse = voucherService.createVoucher(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponseFactory.success(
                        voucherResponse
                ));
    }
}
