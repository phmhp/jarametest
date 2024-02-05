package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import SiliconDream.JaraMe.domain.Notification;

import java.util.Collection;
import java.util.List;
/* 서버 배포 테스트 중 에러나서 주석처리
@Repository

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsRead(Long userId, boolean isRead);

    List<Notification> findByUserId(Long userId);
}
*/