package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.AddresesEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddresesEntity, Long> {
    List<AddresesEntity> findByUser_IdAndIsDeleteFalse(Long userId);

}
