package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long>, JpaSpecificationExecutor<ProductsEntity> {
//    @Query(
//            value = """
//                        SELECT DISTINCT p
//                        FROM ProductsEntity p
//                        LEFT JOIN p.images i
//                        LEFT JOIN p.inventories inv
//                        WHERE p.isDelete = false
//                        AND (i.isDelete = false OR i.id IS NULL)
//                        AND (inv.isDelete = false OR inv.id IS NULL)
//                    """,
//            countQuery = """
//                        SELECT COUNT(p)
//                        FROM ProductsEntity p
//                        WHERE p.isDelete = false
//                    """
//    )
//    Page<ProductsEntity> findAllActive(Pageable pageable);

    @Query("select count(p) from ProductsEntity p where p.isDelete = false")
    Long countProduct();

}
