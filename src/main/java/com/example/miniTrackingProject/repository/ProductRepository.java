package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long>, JpaSpecificationExecutor<ProductsEntity> {

    @Query("select sum(p.originalPrice * i.quantityInStock) from ProductsEntity p JOIN p.inventories i where p.isDelete = false and i.isDelete = false")
    BigDecimal getTotalPriceProuct();

    @Query("select count(p) from ProductsEntity p where p.isDelete = false")
    Long countProduct();

    @Query("select count(p) from ProductsEntity p JOIN p.inventories i where p.isDelete = false and i.isDelete = false and i.quantityInStock < 10")
    Long getTotalAlmostOutOfStock();

}
