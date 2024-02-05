package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import SiliconDream.JaraMe.domain.User;
import SiliconDream.JaraMe.dto.DailyMissionDTO;
import SiliconDream.JaraMe.service.DailyMissionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dailyMission")
public class DailyMissionController {
    private final DailyMissionService dailyMissionService;

    @Autowired
    public DailyMissionController(DailyMissionService dailyMissionService) {
        this.dailyMissionService = dailyMissionService;
    }

    //오늘의 미션 조회 => 테스트완료 / 예외처리 전
    @GetMapping("/get")
    public Optional<List<DailyMissionDTO>> getDailyMission(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
        // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();
        Optional<List<DailyMissionDTO>> dailyMissionDTOList = dailyMissionService.getDailyMission(userId, LocalDate.now());
        return dailyMissionDTOList;
    }
}
