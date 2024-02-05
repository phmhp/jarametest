package SiliconDream.JaraMe.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.repository.UserRepository;
import SiliconDream.JaraMe.service.UserService;
import SiliconDream.JaraMe.service.UserServiceImpl;

import java.util.List;

@Component
public class CheckInResetScheduler {
    private final UserRepository userRepository;
    private final UserService userService;

    public CheckInResetScheduler(UserRepository userRepository,UserService userService){
        this.userRepository=userRepository;
        this.userService=userService;
    }

    //출석체크 컬럼 자정 초기화
    @Scheduled(cron ="0 0 0 * * *")
    public void resetCheckInStatus() {
        //userRepository.resetCheckInStatus();
        List<User> allUsers = userService.getAllUsers();

        for (User user: allUsers){
            user.setCheckIn(false);
            userService.saveUser(user);
        }
    }
}
