package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.common.StatusVoucher;
import com.example.miniTrackingProject.entity.VouchersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<VouchersEntity, Long> {

    @Query("select v from VouchersEntity v " + "where v.id <= :id and v.status = ACTIVE and v.isDelete = false and :time between v.startDate and v.endDate")
    Optional<VouchersEntity> findVoucher(@Param("id") Long id, @Param("time") LocalDateTime time);
}
