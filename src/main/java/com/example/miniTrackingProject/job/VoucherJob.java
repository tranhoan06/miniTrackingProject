package com.example.miniTrackingProject.job;

import com.example.miniTrackingProject.common.StatusVoucher;
import com.example.miniTrackingProject.entity.VouchersEntity;
import com.example.miniTrackingProject.repository.VoucherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class VoucherJob {

    private final VoucherRepository voucherRepository;

    @Scheduled(cron = "0 * * * * *") // chạy mỗi phút
    @Transactional
    public void updateVoucherStatus() {
        log.info("=== Voucher Job started ===");
        LocalDateTime time = LocalDateTime.now();

        List<VouchersEntity> vouchersEntityListDraft =
                voucherRepository.findByStartDateBeforeAndStatus(time, StatusVoucher.DRAFT);

        for (VouchersEntity vouchers : vouchersEntityListDraft) {
            vouchers.setStatus(StatusVoucher.ACTIVE);
        }

        log.info("Voucher cần activate: {}", vouchersEntityListDraft.size());

        List<VouchersEntity> vouchersEntityListActive =
                voucherRepository.findByEndDateBeforeAndStatus(time, StatusVoucher.ACTIVE);
        for (VouchersEntity vouchers : vouchersEntityListActive) {
            vouchers.setStatus(StatusVoucher.INACTIVE);
        }

        log.info("Voucher cần inactive: {}", vouchersEntityListActive.size());

        log.info("=== Voucher Job finished ===");
    }
}
