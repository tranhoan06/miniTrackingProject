package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.SecurityHelper;
import com.example.miniTrackingProject.common.StatusVoucher;
import com.example.miniTrackingProject.dto.request.VoucherRequest;
import com.example.miniTrackingProject.dto.response.VoucherResponse;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.entity.VouchersEntity;
import com.example.miniTrackingProject.mapper.VoucherMapper;
import com.example.miniTrackingProject.repository.VoucherRepository;
import com.example.miniTrackingProject.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final SecurityHelper securityHelper;
    private final VoucherMapper voucherMapper;
    private final VoucherRepository voucherRepository;

    @Override
    public VoucherResponse createVoucher(VoucherRequest request) {
        UserEntity userEntity = securityHelper.getCurrentUser();

        VouchersEntity vouchersEntity = new VouchersEntity();
        vouchersEntity.setCode(request.getCode());
        vouchersEntity.setDescription(request.getDescription());
        vouchersEntity.setSeller(userEntity);
        vouchersEntity.setDiscountType(request.getDiscountType());
        vouchersEntity.setDiscountValue(request.getDiscountValue());
        vouchersEntity.setMaxDiscount(request.getMaxDiscount());
        vouchersEntity.setMinOrderAmount(request.getMinOrderAmount());
        vouchersEntity.setQuantity(request.getQuantity());
        vouchersEntity.setEndDate(request.getEndDate());
        vouchersEntity.setStartDate(request.getStartDate());
        vouchersEntity.setStatus(StatusVoucher.DRAFT);
        vouchersEntity.setIsDelete(false);
        vouchersEntity.setCreatedAt(LocalDateTime.now());

        voucherRepository.save(vouchersEntity);

        return voucherMapper.toVoucherResponse(vouchersEntity);
    }
}
