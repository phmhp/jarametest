package SiliconDream.JaraMe.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import SiliconDream.JaraMe.domain.JaraUs;
import SiliconDream.JaraMe.domain.Recurrence;
import SiliconDream.JaraMe.domain.Schedule;
import SiliconDream.JaraMe.repository.JaraUsRepository;
import SiliconDream.JaraMe.repository.ScheduleRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final JaraUsRepository jaraUsRepository;
    private ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,
                               JaraUsRepository jaraUsRepository) {
        this.scheduleRepository = scheduleRepository;
        this.jaraUsRepository = jaraUsRepository;
    }

    //미션 시작일,종료일 사이의 반복요일의 정확한 날짜 구해서 schedule테이블에 기록하기
    public void jaraUsScheduling(JaraUs jaraUs) {
        Long jaraUsId = jaraUs.getJaraUsId();
        log.info("jaraUsId:{}", jaraUsId);
        LocalDate startDate = jaraUs.getStartDate();
        log.info("startDate:{}",startDate);
        LocalDate endDate = jaraUs.getEndDate();
        log.info("endDate:{}",endDate);
        Set<Recurrence> recurrenceSet = jaraUs.getRecurrence();
        log.info("recurrenceSet:{}", recurrenceSet);

        //List<LocalDate> realDates = new ArrayList<>();
        LocalDate nowDate = startDate;

        while (nowDate.isBefore(endDate.plusDays(1))) {
            log.info("while-nowDate:{}",nowDate);
                for (Recurrence recurrence : recurrenceSet) {
                 if (DayOfWeek.valueOf(recurrence.getValue()).equals(nowDate.getDayOfWeek())) {

                       Schedule schedule = new Schedule();
                    schedule.setJaraUs(jaraUsRepository.findByJaraUsId(jaraUsId));
                    schedule.setScheduleDate(nowDate);
                    log.info("will be save - nowDate:{}",nowDate);
                    scheduleRepository.save(schedule);
                }

            } nowDate = nowDate.plusDays(1);


        }
    }
}
