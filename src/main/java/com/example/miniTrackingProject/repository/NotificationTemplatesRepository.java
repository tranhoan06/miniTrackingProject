package com.example.miniTrackingProject.repository;

import com.example.miniTrackingProject.entity.NotificationTemplatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTemplatesRepository extends JpaRepository<NotificationTemplatesEntity, Long> {
    Optional<NotificationTemplatesEntity> findByStatusCodeAndRecipientType(String statusCode, String recipientType);
}
