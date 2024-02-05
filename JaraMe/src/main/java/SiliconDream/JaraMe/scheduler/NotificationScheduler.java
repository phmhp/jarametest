package SiliconDream.JaraMe.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.service.JaraUsServiceImpl;
import siliconDream.jaraMe.service.NotificationServiceImpl;
import siliconDream.jaraMe.service.ToDoListServiceImpl;
import siliconDream.jaraMe.service.UserService;

import java.util.List;

@Service
public class NotificationScheduler {

    @Autowired
    private UserService userService;
    @Autowired
    private ToDoListServiceImpl toDoListService;
    @Autowired
    private JaraUsServiceImpl jaraUsService;
    @Autowired
    private NotificationServiceImpl notificationService;

    @Scheduled(cron = "0 0 22 * * *") // 매일 오후 10시 실행
    public void generateDailyNotifications() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            boolean hasIncompleteTasks = toDoListService.hasIncompleteTasks(user.getUserId());
            boolean hasIncompleteMissions = jaraUsService.hasIncompleteMissions(user.getUserId());
/* 서버 배포 테스트 중 에러나서 주석처리
            if (hasIncompleteTasks || hasIncompleteMissions) {
                notificationService.createNotification(user.getUserId(), "아직 완료되지 않은 활동이 있습니다.");
            }*/
        }
    }
}

