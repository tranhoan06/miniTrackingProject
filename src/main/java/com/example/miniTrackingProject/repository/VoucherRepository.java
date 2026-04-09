package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.VouchersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<VouchersEntity, Long> {
}
