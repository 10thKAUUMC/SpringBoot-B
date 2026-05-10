package com.example.app.domain.notification.dto.response;

import com.example.app.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponse {
    private Long notificationId;
    private String type;
    private String content;
    private Boolean isRead;
    private LocalDateTime createdAt;

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getNotificationId())
                .type(notification.getType())
                .content(notification.getContent())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
