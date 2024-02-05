package SiliconDream.JaraMe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.DailyMission;
import siliconDream.jaraMe.domain.Schedule;
import siliconDream.jaraMe.dto.DailyMissionDTO;

import siliconDream.jaraMe.repository.DailyMissionRepository;
import siliconDream.jaraMe.repository.JaraUsRepository;
import siliconDream.jaraMe.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
@Slf4j

@Service
public class DailyMissionServiceImpl implements DailyMissionService{

    private final UserRepository userRepository;
    private final JaraUsRepository jaraUsRepository;
    private final DailyMissionRepository dailyMissionRepository;

    public DailyMissionServiceImpl(UserRepository userRepository,
                                   JaraUsRepository jaraUsRepository,
                                   DailyMissionRepository dailyMissionRepository) {
        this.userRepository = userRepository;
        this.jaraUsRepository = jaraUsRepository;
        this.dailyMissionRepository = dailyMissionRepository;
    }

    public void makeDailyMission(Long userId, Long jaraUsId){
             DailyMission dailyMission = new DailyMission();
            //dailyMission.setDailyMissionDate(); => 미션 하면 날짜 기록

            dailyMission.setUser(userRepository.findByUserId(userId));
            dailyMission.setJaraUs(jaraUsRepository.findByJaraUsId(jaraUsId));
            dailyMission.setScheduleDate(LocalDate.now());
            dailyMissionRepository.save(dailyMission);
        }

    //오늘의 미션 조회
    public Optional<List<DailyMissionDTO>> getDailyMission(Long userId, LocalDate todayDate) {
//userId와 todayDate 전달받기

        //ScheduleDate 테이블에서 매개변수로 받은 todayDate 와 scheduleId로 필터링해서 '오늘의 미션' 알아내기
        List<DailyMissionDTO> dailyMissionDTOList= dailyMissionRepository.findDailyMissionDTOByScheduleDateAndUser_UserId(todayDate,userId);
        log.info("dailyMissionDTOList:{}",dailyMissionDTOList);
        return Optional.of(dailyMissionDTOList);

    }
}
