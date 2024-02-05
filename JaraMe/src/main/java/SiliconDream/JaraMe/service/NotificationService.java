package SiliconDream.JaraMe.service;

import siliconDream.jaraMe.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    /**
     * 사용자에게 새로운 알림을 생성합니다.
     *
     * @param userId  알림을 받을 사용자의 ID
     * @param message 알림 메시지
     */
    /* 서버 배포 테스트 중 에러나서 주석처리
    void createNotification(Long userId, String message);

    /**
     * 특정 사용자의 모든 알림을 조회합니다.
     *
     * @param userId 알림을 조회할 사용자의 ID
     * @return 사용자의 알림 목록을 담은 NotificationDTO 리스트
     */
    /* 서버 배포 테스트 중 에러나서 주석처리
    List<NotificationDTO> getUserNotifications(Long userId);

    boolean markAsRead(Long notificationId);

    List<NotificationDTO> getAllNotificationsForUser(Long userId);
    */
}

