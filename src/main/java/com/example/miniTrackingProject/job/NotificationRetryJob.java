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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Value("${spring.mail.username}")
    private String from;

    @Scheduled(fixedDelay = 60_000)
    @Transactional
    public void sendConfirmedOrderNotifications() {
        int pageSize = 1;
        int pageNumber = 0;

        LocalDateTime since = LocalDateTime.now().minusMinutes(10);

        Page<OrdersEntity> page;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            page = orderRepository
                    .findByOrderStatusAndUpdatedAtAfterOrderByUpdatedAtAsc(
                            OrderStatus.CONFIRMED,
                            since,
                            pageable
                    );

            List<OrdersEntity> orders = page.getContent();

            for (OrdersEntity order : orders) {
                try {
                    processOrder(order);
                } catch (Exception e) {
                    log.error("Unexpected error processing orderId={}", order.getId(), e);
                }
            }

            pageNumber++;

        } while (page.hasNext());
    }

    private void processOrder(OrdersEntity order) {
        if (order.getId() == null) return;

        UserEntity buyer = order.getBuyer();
        if (buyer == null || buyer.getEmail() == null || buyer.getEmail().isBlank()) return;

        String statusCode = OrderStatus.CONFIRMED.name();
        String recipientType = RoleEnum.BUYER.name();

        // 1. Đã gửi thành công rồi → bỏ qua
        boolean alreadySent = notificationsRepository
                .existsByOrders_IdAndTemplates_StatusCodeAndDeliveryStatusAndNotificationType(
                        order.getId(), statusCode, "SUCCESS", "MAIL");
        if (alreadySent) {
            log.debug("Already SUCCESS orderId={}", order.getId());
            return;
        }

        // 2. Đã tồn tại bản ghi → không tạo thêm
        boolean alreadyExists = notificationsRepository
                .existsByOrders_IdAndTemplates_StatusCodeAndNotificationType(
                        order.getId(), statusCode, "MAIL");
        if (alreadyExists) {
            log.debug("Already exists (PENDING/FAILED) orderId={}", order.getId());
            return;
        }

        // 3. Lấy template
        NotificationTemplatesEntity tpl = templatesRepository
                .findByStatusCodeAndRecipientType(statusCode, recipientType)
                .orElse(null);
        if (tpl == null) {
            log.warn("No template found for statusCode={} recipientType={}", statusCode, recipientType);
            return;
        }

        // 4. Build nội dung
        String buyerName = (buyer.getFullname() != null && !buyer.getFullname().isBlank())
                ? buyer.getFullname()
                : buyer.getUsername();

        String subject = tpl.getSubject()
                .replace("{{orderCode}}", String.valueOf(order.getId()))
                .replace("{{name}}", buyerName);

        String content = tpl.getContent()
                .replace("{{orderCode}}", String.valueOf(order.getId()))
                .replace("{{status}}", statusCode)
                .replace("{{name}}", buyerName);

        // 5. Lưu notification với PENDING
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
        log.info("Created notification id={} for orderId={}", noti.getId(), order.getId());

        // 6. Gửi mail
        try {
            mailService.send(noti.getEmailTo(), noti.getTitle(), noti.getContent(), from);
            noti.setDeliveryStatus("SUCCESS");
            log.info("Mail sent SUCCESS orderId={} to={}", order.getId(), noti.getEmailTo());
        } catch (Exception ex) {
            noti.setDeliveryStatus("FAILED");
            log.error("Mail FAILED orderId={} to={}", order.getId(), noti.getEmailTo(), ex);
        }

        notificationsRepository.save(noti);
    }
}