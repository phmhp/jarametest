package SiliconDream.JaraMe.service;

import siliconDream.jaraMe.domain.Schedule;
import siliconDream.jaraMe.dto.DailyMissionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyMissionService {

    void makeDailyMission(Long userId, Long jaraUsId);
   Optional<List<DailyMissionDTO>> getDailyMission(Long userId, LocalDate todayDate) ;

}
