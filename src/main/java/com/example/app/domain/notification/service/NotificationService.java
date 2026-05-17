package com.example.app.domain.notification.service;

import com.example.app.domain.notification.dto.response.NotificationResponse;
import com.example.app.domain.notification.entity.Notification;
import com.example.app.domain.notification.repository.NotificationRepository;
import com.example.app.domain.user.entity.User;
import com.example.app.domain.user.repository.UserRepository;
import com.example.app.global.exception.BusinessException;
import com.example.app.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public Page<NotificationResponse> getNotifications(Long userId, Pageable pageable) {
        return notificationRepository.findByUser_UserId(userId, pageable)
                .map(NotificationResponse::from);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUser_UserIdAndIsRead(userId, false);
    }

    @Transactional
    public NotificationResponse markAsRead(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if (!notification.getUser().getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        notification.markAsRead();
        return NotificationResponse.from(notification);
    }

    @Transactional
    public void send(Long userId, String type, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Notification notification = Notification.builder()
                .user(user)
                .type(type)
                .content(content)
                .build();

        notificationRepository.save(notification);
    }
}
