package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.PayMethodEnum;
import com.example.miniTrackingProject.common.PaymentStatus;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

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

    Page<OrdersEntity> findBySeller(UserEntity seller, Pageable pageable);
}
