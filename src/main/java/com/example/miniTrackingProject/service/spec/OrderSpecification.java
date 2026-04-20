package com.example.miniTrackingProject.service.spec;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<OrdersEntity> filterStatusOrder(UserEntity user, OrderStatus orderStatus) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Phân quyền truy cập
            if (user.getRole() == RoleEnum.SELLER) {
                predicates.add(cb.equal(root.get("seller").get("id"), user.getId()));
            } else if (user.getRole() == RoleEnum.BUYER) {
                predicates.add(cb.equal(root.get("buyer").get("id"), user.getId()));
            }

            if (orderStatus != null) {
                predicates.add(cb.equal(root.get("orderStatus"), orderStatus));
            } else {
                // Nếu không chọn, lấy danh sách mặc định dựa trên Role
                List<OrderStatus> defaultStatuses = getDefaultStatusesByRole(user.getRole());
                if (!defaultStatuses.isEmpty()) {
                    predicates.add(root.get("orderStatus").in(defaultStatuses));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<OrderStatus> getDefaultStatusesByRole(RoleEnum role) {
        if (role == RoleEnum.SELLER) {
            return List.of(
                    OrderStatus.RETURN_PENDING, OrderStatus.RETURNED,
                    OrderStatus.WAREHOUSE_RECEIVED, OrderStatus.PENDING,
                    OrderStatus.IN_TRANSIT, OrderStatus.REFUNDED
            );
        } else if (role == RoleEnum.BUYER) {
            return List.of(
                    OrderStatus.CONFIRMED, OrderStatus.SHIPPED,
                    OrderStatus.IN_TRANSIT, OrderStatus.DELIVERED,
                    OrderStatus.RETURNED
            );
        }
        return List.of();
    }
}
