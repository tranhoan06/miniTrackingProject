package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.InventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByProduct_IdInAndIsDeleteFalse(Collection<Long> productIds);

    // TODO: sửa từ tìm 1 sang tìm tìm nhiều - done
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @Query("SELECT i FROM InventoryEntity i WHERE i.product.id = :productId AND i.isDelete = false")
//    Optional<InventoryEntity> findByProductIdForUpdate(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InventoryEntity i WHERE i.product.id IN :productIds AND i.isDelete = false")
    List<InventoryEntity> findByProductIdsForUpdate(Collection<Long> productIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from InventoryEntity i where i.product.id =: proudctId and i.isDelete = false")
    List<InventoryEntity> findAllByProductIdForUpdate(@Param("proudctId") Long productId);
}
