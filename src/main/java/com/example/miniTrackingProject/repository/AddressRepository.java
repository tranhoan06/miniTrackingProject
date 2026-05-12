package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.AddressesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressesEntity, Long> {
    List<AddressesEntity> findByUser_IdAndIsDeleteFalse(Long userId);

}
