package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.CategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoriesEntity, Long> {
    boolean existsByCategoryNameAndParent_Id(String categoryName, Long parentId);

    boolean existsByCategoryNameAndParentIsNull(String categoryName);

    // Tìm category trùng tên NHƯNG KHÔNG PHẢI chính nó - cấp parent có parent = null
    boolean existsByCategoryNameAndParentIsNullAndIdNot(String name, Long id);

    // Tìm category trùng tên NHƯNG KHÔNG PHẢI chính nó - cấp children
    boolean existsByCategoryNameAndParent_IdAndIdNot(String name, Long parentId, Long id);
}
