package com.example.miniTrackingProject.job;

import com.example.miniTrackingProject.common.OrderStatus;
import com.example.miniTrackingProject.common.RoleEnum;
import com.example.miniTrackingProject.entity.NotificationTemplatesEntity;
import com.example.miniTrackingProject.entity.NotificationsEntity;
import com.example.miniTrackingProject.entity.OrdersEntity;
import com.example.miniTrackingProject.entity.UserEntity;
import com.example.miniTrackingProject.repository.NotificationTemplatesRepository;
import com.example.miniTrackingProject.repository.NotificationsRepository;
import com.example.miniTrackingProject.repository.OrderRepository;
import com.example.miniTrackingProject.service.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRetryJob {

    private final OrderRepository orderRepository;
    private final NotificationTemplatesRepository templatesRepository;
    private final NotificationsRepository notificationsRepository;
    private final MailService mailService;

    @Scheduled(fixedDelay = 60_000) // mỗi 60s
    @Transactional
    public void sendConfirmedOrderNotifications() {

        LocalDateTime since = LocalDateTime.now().minusMinutes(10);

        List<OrdersEntity> orders = orderRepository
                .findTop100ByOrderStatusAndUpdatedAtAfterOrderByUpdatedAtAsc(OrderStatus.CONFIRMED, since);

        for (OrdersEntity order : orders) {
            if (order.getId() == null) continue;

            UserEntity buyer = order.getBuyer();
            if (buyer == null || buyer.getEmail() == null || buyer.getEmail().isBlank()) continue;

            String statusCode = OrderStatus.CONFIRMED.name();
            String recipientType = RoleEnum.BUYER.name();

            boolean alreadySent = notificationsRepository
                    .existsByOrders_IdAndTemplates_StatusCodeAndDeliveryStatusAndNotificationType(
                            order.getId(),
                            statusCode,
                            "SUCCESS",
                            "MAIL"
                    );
            if (alreadySent) continue;

            NotificationTemplatesEntity tpl = templatesRepository
                    .findByStatusCodeAndRecipientType(statusCode, recipientType)
                    .orElse(null);

            if (tpl == null) {
                continue;
            }

            String subject = tpl.getSubject()
                    .replace("{{orderId}}", String.valueOf(order.getId()));

            String content = tpl.getContent()
                    .replace("{{orderId}}", String.valueOf(order.getId()))
                    .replace("{{status}}", statusCode);

            NotificationsEntity noti = NotificationsEntity.builder()
                    .user(buyer)
                    .orders(order)
                    .templates(tpl)
                    .title(subject)
                    .emailTo(buyer.getEmail())
                    .content(content)
                    .deliveryStatus("PENDING")
                    .notificationType("MAIL")
                    .build();

            noti = notificationsRepository.save(noti);

            try {
                mailService.send(noti.getEmailTo(), noti.getTitle(), noti.getContent());
                noti.setDeliveryStatus("SUCCESS");
            } catch (Exception ex) {
                noti.setDeliveryStatus("FAILED");
            }

            notificationsRepository.save(noti);
        }
    }
}