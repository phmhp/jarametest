package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.CalendarDTO;
import SiliconDream.JaraMe.dto.NoticeDTO;
import SiliconDream.JaraMe.repository.JaraUsRepository;
import SiliconDream.JaraMe.service.JaraUsService;
import SiliconDream.JaraMe.service.MissionHistoryService;
import SiliconDream.JaraMe.service.NoticeService;
import SiliconDream.JaraMe.service.ScheduleService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final ScheduleService scheduleService;
    private final MissionHistoryService missionHistoryService;
    private final JaraUsRepository jaraUsRepository;

    public NoticeController(NoticeService noticeService,
                            ScheduleService scheduleService,
                            MissionHistoryService missionHistoryService,
                            JaraUsRepository jaraUsRepository){
        this.noticeService=noticeService;
        this.scheduleService =scheduleService;
        this.missionHistoryService = missionHistoryService;
        this.jaraUsRepository = jaraUsRepository;
    }

    //미션완주,리액션통계
    @GetMapping("/get")
    public Optional<NoticeDTO> missionFinishNotice(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
        // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();
        Optional<NoticeDTO> noticeDTO =noticeService.findNoticeMessageByUserIdAndNoticeStatus(userId);
        return noticeDTO;
    }

    //자라어스 생성 시 스케줄링 테스트 용도
    @PostMapping("/scheduling")
    public void scheduling (@RequestParam Long jaraUsId){
        scheduleService.jaraUsScheduling(jaraUsRepository.findByJaraUsId(jaraUsId));
    }

    //캘린더
    @GetMapping("calendar")
    public Optional<CalendarDTO> calendar(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectedDate, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
        // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();
        Optional<CalendarDTO> calendarDTO = noticeService.getCalendar(selectedDate,userId);
        return calendarDTO;
    }
}
