package com.example.miniTrackingProject.service;

import com.example.miniTrackingProject.dto.request.VoucherRequest;
import com.example.miniTrackingProject.dto.response.VoucherResponse;

public interface VoucherService {

    VoucherResponse createVoucher(VoucherRequest request);
}
