package com.example.app.domain.notification.repository;

import com.example.app.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findByUser_UserId(Long userId, Pageable pageable);
    long countByUser_UserIdAndIsRead(Long userId, Boolean isRead);
}
