package SiliconDream.JaraMe.scheduler;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import SiliconDream.JaraMe.domain.DailyMission;
import SiliconDream.JaraMe.domain.Schedule;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.DailyMissionRecordDTO;
import SiliconDream.JaraMe.repository.*;
import SiliconDream.JaraMe.service.DailyMissionService;
import SiliconDream.JaraMe.service.UserService;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DailyMissionUpdateScheduler {
    private final UserRepository userRepository;

    private final DailyMissionRepository dailyMissionRepository;
    private final MissionHistoryRepository missionHistoryRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final JoinUsersRepository joinUsersRepository;
    private final DailyMissionService dailyMissionService;

    public DailyMissionUpdateScheduler(DailyMissionRepository dailyMissionRepository,
                                       MissionHistoryRepository missionHistoryRepository,
                                       ScheduleRepository scheduleRepository,
                                       UserService userService,
                                       JoinUsersRepository joinUsersRepository,
                                       DailyMissionService dailyMissionService,
                                       UserRepository userRepository) {

        this.dailyMissionRepository = dailyMissionRepository;
        this.missionHistoryRepository = missionHistoryRepository;
        this.scheduleRepository = scheduleRepository;
        this.userService = userService;
        this.joinUsersRepository = joinUsersRepository;
        this.dailyMissionService = dailyMissionService;
        this.userRepository = userRepository;
    }

    @Transactional
    @Scheduled(cron = "30 06 3 * * *")
    public void transferDailyMission() {
        log.info("start  ?");
        //모든 유저
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            //데일리미션테이블에 레코드가 있는 경우 => 미션기록테이블에 복사 후 전체 삭제
            List<DailyMission> doneDailyMission = dailyMissionRepository.findByUser_UserId(user.getUserId());
            log.info("doneDateMission:{}", doneDailyMission);
            //데일리미션테이블에 레코드가 있는 경우
            if (doneDailyMission.size() != 0) {
                log.info("doneDailyMission.size:{}", doneDailyMission.size());
                for (DailyMission one : doneDailyMission) {
                    log.info("one");
                    //미션기록테이블에 저장
                    DailyMissionRecordDTO dailyMissionRecordDTO = new DailyMissionRecordDTO();

                    log.info("one.isDailyMissionResult:{}", one.isDailyMissionResult());

                    //미션인증을 하지않은 경우
                    if (!one.isDailyMissionResult()) {
                        dailyMissionRecordDTO.setMissionDate(one.getScheduleDate());
                        dailyMissionRecordDTO.setJaraUs(one.getJaraUs());
                        dailyMissionRecordDTO.setUser(user);
                        dailyMissionRecordDTO.setMissionResult(one.isDailyMissionResult());
                    }
                    else if (one.isDailyMissionResult()) { //미션인증을 한 경우
                        dailyMissionRecordDTO.setMissionDate(one.getScheduleDate());
                        dailyMissionRecordDTO.setJaraUs(one.getJaraUs());
                        dailyMissionRecordDTO.setUser(user);
                        dailyMissionRecordDTO.setMissionResult(one.isDailyMissionResult());
                        dailyMissionRecordDTO.setMissionPost(one.getMissionPost());
                    }

                    log.info("set");
                    missionHistoryRepository.saveDailyMissionRecord(dailyMissionRecordDTO);
                    log.info("save");

                    dailyMissionRepository.deleteByUserUserId(user.getUserId());

                    log.info("delete-done");

                }
            }
            log.info("userId:{}", user.getUserId());
            //해당 유저가 참여하고 있는 자라어스 식별자들을 얻은 후,
            Optional<List<Long>> joinedJaraUsIds = joinUsersRepository.findJaraUs_jaraUsIdsByUser_userId(user.getUserId());
            log.info("joinedJaraUsIds:{}", joinedJaraUsIds);

            for (Long jaraUsId : joinedJaraUsIds.get()) {
                log.info("for-");
                //얻어온 자라어스 식별자들 중 오늘 인증하는 날인 미션이라면 스케줄 레코드를 가져옴
                Optional<Long> todayScheduledJaraUsId = scheduleRepository.findJaraUs_JaraUsIdByScheduleDateAndJaraUsId(LocalDate.now(), jaraUsId);

                if (todayScheduledJaraUsId.isPresent()) {
                    log.info("if-todayScheduledJaraUsId. isPresent? :");
                    //스케줄 레코드와 유저식별자를 통해 오늘의 미션을 업데이트함
                    dailyMissionService.makeDailyMission(user.getUserId(), jaraUsId);
                    log.info("done");
                }
            }


        }
    }
}
