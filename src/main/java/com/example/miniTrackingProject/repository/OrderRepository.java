package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.PayMethodEnum;
import com.example.miniTrackingProject.common.PaymentStatus;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.repository.projection.OrderOverviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long>, JpaSpecificationExecutor<OrdersEntity> {

    @Query("SELECT o FROM OrdersEntity o " +
            "WHERE o.id IN :ids " +
            "AND o.buyer = :buyer " +
            "AND o.orderStatus = :orderStatus " +
            "AND o.paymentMethod = :paymentMethod " +
            "AND o.paymentStatus = :paymentStatus")
    List<OrdersEntity> findByIdAndBuyerAndOrderStatusAndPaymentMethodAndPaymentStatus(@Param("ids") List<Long> ids,
                                        @Param("buyer") UserEntity buyer,
                                        @Param("orderStatus") OrderStatus orderStatus,
                                        @Param("paymentMethod") PayMethodEnum paymentMethod,
                                        @Param("paymentStatus") PaymentStatus paymentStatus);

    // Lấy các đơn hàng KHÔNG THUỘC danh sách (cho đơn hàng bình thường)
    Page<OrdersEntity> findBySellerAndOrderStatusNotIn(UserEntity seller, List<OrderStatus> statuses, Pageable pageable);

    // Lấy các đơn hàng THUỘC danh sách (cho đơn hàng trả về)
    Page<OrdersEntity> findBySellerAndOrderStatusIn(UserEntity seller, List<OrderStatus> statuses, Pageable pageable);

    Optional<OrdersEntity> findByTrackingCode(String trackingCode);

    @Query(value = "SELECT " +
            "COALESCE(SUM(final_amount), 0) AS totalAmount, " +
            "COUNT(*) AS totalOrder, " +
            "COUNT(CASE WHEN order_status = 'PENDING' THEN 1 ELSE 0 END) AS totalPending, " +
            "COUNT(CASE WHEN order_status = 'IN_TRANSIT' THEN 1 ELSE 0 END) AS totalIntransit, " +
            "COUNT(CASE WHEN order_status = 'CANCELLED' THEN 1 ELSE 0 END) AS totalCancel, " +
            "COUNT(CASE WHEN order_status = 'FAILED' THEN 1 ELSE 0 END) AS totalFailed, " +
            "COUNT(CASE WHEN order_status = 'RETURNED' THEN 1 ELSE 0 END) AS totalReturn, " +
            "0 AS awaitingInspection, " +
            "0 AS totalRefunds " +
            "FROM orders WHERE seller_id = :#{#seller.id}",
            nativeQuery = true)
    OrderOverviewProjection getOverviewBySeller(@Param("seller") UserEntity seller);
}
