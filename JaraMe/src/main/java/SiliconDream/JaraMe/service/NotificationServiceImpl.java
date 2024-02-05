package SiliconDream.JaraMe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.Notification;
import siliconDream.jaraMe.dto.NotificationDTO;
/* 서버 배포 테스트 중 에러나서 주석처리
import siliconDream.jaraMe.repository.NotificationRepository;
*/
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    /* 서버 배포 테스트 중 에러나서 주석처리
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

     */
/*서버배포 테스트 중 에러나서 주석처리
    @Override
    public void createNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setCreatedDateTime(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdAndIsRead(userId, false)
                .stream()
                .map(notification -> new NotificationDTO(
                        notification.getId(),
                        notification.getUserId(),
                        notification.getMessage(),
                        notification.getCreatedDateTime(),
                        notification.isRead())
                )
                .collect(Collectors.toList());
    }

    @Override
    public boolean markAsRead(Long notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if (notification.isPresent()) {
            Notification n = notification.get();
            n.setRead(true);
            notificationRepository.save(n);
            return true;
        }
        return false;
    }

    @Override
    public List<NotificationDTO> getAllNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .sorted(Comparator.comparing(Notification::getCreatedDateTime).reversed())
                .map(n -> new NotificationDTO(n.getId(), n.getUserId(), n.getMessage(), n.getCreatedDateTime(), n.isRead()))
                .collect(Collectors.toList());
    }
*/
}

