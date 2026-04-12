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

    @Query("select v from VouchersEntity v " + "where v.id <= :id and v.status = ACTIVE and v.isDelete = false")
    Optional<VouchersEntity> findVoucher(@Param("id") Long id);

    @Query("select v from VouchersEntity v " + "where v.startDate <= :start_date and v.status = :status and v.isDelete = false")
    List<VouchersEntity> findByStartDateBeforeAndStatus(@Param("start_date") LocalDateTime start_date, @Param("status") StatusVoucher status);

    @Query("select v from VouchersEntity v " + "where v.endDate <= :end_date and v.status = :status and v.isDelete = false")
    List<VouchersEntity> findByEndDateBeforeAndStatus(@Param("end_date") LocalDateTime end_date, StatusVoucher status);
}
