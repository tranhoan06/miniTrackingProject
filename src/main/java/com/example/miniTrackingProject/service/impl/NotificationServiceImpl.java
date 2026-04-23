package com.example.miniTrackingProject.service.impl;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.common.SecurityHelper;
import com.example.miniTrackingProject.entity.NotificationTemplatesEntity;
import com.example.miniTrackingProject.entity.NotificationsEntity;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.repository.NotificationTemplatesRepository;
import com.example.miniTrackingProject.repository.NotificationsRepository;
import com.example.miniTrackingProject.service.MailService;
import com.example.miniTrackingProject.service.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationTemplatesRepository templatesRepository;
    private final NotificationsRepository notificationsRepository;
    private final MailService mailService;
    private final SecurityHelper securityHelper;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Override
    @Transactional
    public void notifyOrderStatus(UserEntity recipient, OrdersEntity order, OrderStatus status) {

        String recipientType = RoleEnum.BUYER.name();

        NotificationTemplatesEntity tpl = templatesRepository
                .findByStatusCodeAndRecipientType(status.name(), recipientType)
                .orElseThrow(() -> new IllegalStateException("Missing template for " + status + "/" + recipientType));

        String subject = tpl.getSubject()
                .replace("{{orderCode}}", String.valueOf(order.getId()));
        String content = tpl.getContent()
                .replace("{{orderCode}}", String.valueOf(order.getId()))
                .replace("{{name}}", recipient.getFullname())
                .replace("{{status}}", status.name());

        NotificationsEntity noti = NotificationsEntity.builder()
                .user(recipient)
                .orders(order)
                .templates(tpl)
                .title(subject)
                .emailTo(recipient.getEmail())
                .content(content)
                .deliveryStatus("PENDING")
                .notificationType("MAIL")
                .build();
        noti = notificationsRepository.save(noti);

        try {
            mailService.send(noti.getEmailTo(), noti.getTitle(), noti.getContent(), mailFrom);
            noti.setDeliveryStatus("SUCCESS");
        } catch (Exception ex) {
            noti.setDeliveryStatus("FAILED");
        }
        notificationsRepository.save(noti);
    }
}
