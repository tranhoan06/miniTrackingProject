package com.example.miniTrackingProject.service.spec;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<OrdersEntity> filterStatusOrder(UserEntity seller, OrderStatus orderStatus) {
        return new Specification<OrdersEntity>() {
            @Override
            public @Nullable Predicate toPredicate(Root<OrdersEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                // 1. Luôn luôn lọc theo Seller
                predicates.add(criteriaBuilder.equal(root.get("seller").get("id"), seller.getId()));
                if (orderStatus != null) {
                    // Nếu có chọn status cụ thể (ví dụ: chỉ xem đơn RETURNED)
                    predicates.add(criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
                } else {
                    // Nếu không chọn, hiện tất cả các trạng thái thuộc nhóm Return
                    List<OrderStatus> returnStatuses = List.of(
                            OrderStatus.RETURN_PENDING,
                            OrderStatus.RETURNED,
                            OrderStatus.WAREHOUSE_RECEIVED,
                            OrderStatus.PENDING,
                            OrderStatus.IN_TRANSIT,
                            OrderStatus.REFUNDED
                    );
                    predicates.add(root.get("orderStatus").in(returnStatuses));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
