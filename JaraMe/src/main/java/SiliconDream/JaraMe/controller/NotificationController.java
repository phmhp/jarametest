package SiliconDream.JaraMe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siliconDream.jaraMe.dto.NotificationDTO;
import siliconDream.jaraMe.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
/* 서버 배포 테스트 중 에러나서 주석처리
    // 특정 사용자의 알림 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // 특정 알림을 읽음 상태로 업데이트
    @PostMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        boolean updated = notificationService.markAsRead(notificationId);
        if (updated) {
            return ResponseEntity.ok("Notification marked as read");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }
    }

    // 특정 사용자의 모든 알림 조회
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAllUserNotifications(@PathVariable Long userId) {
        List<NotificationDTO> allNotifications = notificationService.getAllNotificationsForUser(userId);
        return ResponseEntity.ok(allNotifications);
    }*/
}


