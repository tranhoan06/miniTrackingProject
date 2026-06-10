package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long>, JpaSpecificationExecutor<ProductsEntity> {

    @EntityGraph(attributePaths = {"seller", "category"})
    @Override
    Page<ProductsEntity> findAll(Specification<ProductsEntity> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"seller", "category"})
    @Query("SELECT p FROM ProductsEntity p WHERE p.id = :id AND p.isDelete = false")
    Optional<ProductsEntity> findDetailById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"seller", "images"})
    @Query("SELECT DISTINCT p FROM ProductsEntity p WHERE p.id IN :ids AND p.isDelete = false")
    List<ProductsEntity> findAllByIdWithSellerAndImages(@Param("ids") Collection<Long> ids);

    @EntityGraph(attributePaths = {"images"})
    @Query("SELECT p FROM ProductsEntity p WHERE p.id = :id AND p.isDelete = false")
    Optional<ProductsEntity> findByIdWithImages(@Param("id") Long id);

    @Query("select sum(p.originalPrice * i.quantityInStock) from ProductsEntity p JOIN p.inventories i where p.isDelete = false and i.isDelete = false")
    BigDecimal getTotalPriceProuct();

    @Query("select count(p) from ProductsEntity p where p.isDelete = false")
    Long countProduct();

    @Query("select count(p) from ProductsEntity p JOIN p.inventories i where p.isDelete = false and i.isDelete = false and i.quantityInStock < 10")
    Long getTotalAlmostOutOfStock();

}
