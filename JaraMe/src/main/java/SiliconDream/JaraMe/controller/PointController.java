package SiliconDream.JaraMe.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.service.PointService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/point")
public class PointController {
    private final PointService pointService;


    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }


    //출석체크 => 테스트 완료 / 예외처리 전
    @PostMapping("/checkIn") //@ResponseBody
    public ResponseEntity<String> checkIn(@RequestParam @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") LocalDateTime dateTime, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
        // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();
        String resultMessage = pointService.checkIn(userId, dateTime);
        if (resultMessage.equals("출석체크되었습니다! (+2포인트)")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }

    //패스권 구매 => 테스트완료 / 예외처리 전
    @PostMapping("/passTicket")
    public ResponseEntity<String> passTicket(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Long userId;
        if (session == null){//todo: 로직 추가하기
        }
        User user = (User) session.getAttribute("user");
        // log.info("log:userId:{}", user.getUserId());
        userId = user.getUserId();

        String resultMessage = pointService.passTicket(userId);
        if (resultMessage.equals("패스권 구입에 성공했습니다!(-60포인트)")) {
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMessage);
        }
    }

}