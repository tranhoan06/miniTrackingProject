package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.AddresesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddresesEntity, Long> {
}
