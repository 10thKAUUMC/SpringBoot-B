package com.example.app.domain.notification.controller;

import com.example.app.domain.notification.dto.response.NotificationResponse;
import com.example.app.domain.notification.service.NotificationService;
import com.example.app.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notification", description = "알림 관련 API")
@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 목록 조회")
    @GetMapping
    public ApiResponse<Page<NotificationResponse>> getNotifications(
            @RequestParam Long userId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ApiResponse.ok(notificationService.getNotifications(userId, pageable));
    }

    @Operation(summary = "읽지 않은 알림 수 조회")
    @GetMapping("/unread-count")
    public ApiResponse<Long> getUnreadCount(@RequestParam Long userId) {
        return ApiResponse.ok(notificationService.getUnreadCount(userId));
    }

    @Operation(summary = "알림 읽음 처리")
    @PatchMapping("/{notificationId}/read")
    public ApiResponse<NotificationResponse> markAsRead(
            @PathVariable Long notificationId,
            @RequestParam Long userId) {
        return ApiResponse.ok(notificationService.markAsRead(userId, notificationId));
    }
}
