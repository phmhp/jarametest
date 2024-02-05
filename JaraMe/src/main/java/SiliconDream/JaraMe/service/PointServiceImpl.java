package SiliconDream.JaraMe.service;

import org.springframework.stereotype.Service;
import siliconDream.jaraMe.domain.PointHistory;
import siliconDream.jaraMe.domain.User;
import siliconDream.jaraMe.repository.PointHistoryRepository;
import siliconDream.jaraMe.repository.PointRepository;
import siliconDream.jaraMe.repository.UserRepository;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final NotificationService notificationService;

    public PointServiceImpl(PointRepository pointRepository,
                            UserRepository userRepository,
                            PointHistoryRepository pointHistoryRepository,
                            NotificationService notificationService) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
        this.pointHistoryRepository = pointHistoryRepository;
        this.notificationService = notificationService;
    }

    //출석체크
    public String checkIn(Long userId, LocalDateTime dateTime) {
        //boolean checkInResult = false;
        String resultMessage = "";
        Optional<User> user = pointRepository.findByUserId(userId);
        if (user.isPresent()) {
            if (dateTime.toLocalDate().equals(LocalDate.now())) { //오늘날짜가 맞는지
                //오늘 출석체크한 적이 없는지
                if (pointRepository.findByUserId(userId).get().isCheckIn() == false) {
                    /**추가**/
                    PointHistory pointHistory = new PointHistory();
                    pointHistory.setPoint(2+userRepository.findByUserId(userId).getPoint());
                    pointHistory.setChangeAmount(2);
                    pointHistory.setPlusOrMinus(true);
                    pointHistory.setTransactionTime(LocalDateTime.now());
                    pointHistory.setNotice(true);
                    pointHistory.setTask("checkIn");
                    pointHistory.setUser(userRepository.findByUserId(userId));

                    pointHistoryRepository.save(pointHistory);

                    pointRepository.updateCheckIn(userId);
                    resultMessage = "출석체크되었습니다! (+2포인트)"; //출석체크 성공
                    // 알림 메시지 생성 및 발송
                    /* 서버 배포 테스트 중 에러나서 주석처리
                    notificationService.createNotification(userId, "출석체크되었습니다! (+2포인트)");
*/

                } else if (pointRepository.findByUserId(userId).get().isCheckIn() == true) {
                    resultMessage = "오늘 이미 출석체크를 하셨습니다!";
                }

            } else if (!dateTime.toLocalDate().equals(LocalDate.now())) {
                resultMessage = "출석체크는 요청 날짜가 잘못됐습니다";
            }
        }
        return resultMessage;
    }

    //패스권 구매
    public String passTicket(Long userId) {
        //dao를 통해 userId로 해당 레코드 가져온 후, point컬럼 값 추출하기
        String resultMessage = "";
        Optional<User> user = pointRepository.findById(userId);
        int point = user.get().getPoint();

        if (point >= 60) {
            resultMessage = "패스권 구입에 성공했습니다!(-60포인트)";
            /**추가**/
            PointHistory pointHistory = new PointHistory();
            pointHistory.setPoint(userRepository.findByUserId(userId).getPoint()-60);
            pointHistory.setChangeAmount(60);
            pointHistory.setPlusOrMinus(false);
            pointHistory.setTransactionTime(LocalDateTime.now());
            pointHistory.setNotice(true);
            pointHistory.setTask("passTicket");
            pointHistory.setUser(userRepository.findByUserId(userId));

            pointHistoryRepository.save(pointHistory);

            //dao 통해 passTicket은 +1, point는 -60
            pointRepository.updatePassTicket(userId);
/* 서버 배포 테스트 중 에러나서 주석처리
            // 알림 메시지 생성 및 발송
            notificationService.createNotification(userId, "패스권을 구입했습니다 (-60포인트)");
*/

        } else if (point < 60) {
            resultMessage = "포인트가 부족합니다.";
        }
        return resultMessage;
    }

    //참여율에 따라 포인트 지급 (pointService? )
    public int pointPlus(Long userId, int changeAmount,Long jaraUsId) {
        int updatedPoint = 0;
        /**추가**/
        PointHistory pointHistory = new PointHistory();
        pointHistory.setPoint(userRepository.findByUserId(userId).getPoint()+changeAmount);
        pointHistory.setChangeAmount(changeAmount);
        pointHistory.setPlusOrMinus(true);
        pointHistory.setTransactionTime(LocalDateTime.now());
        pointHistory.setNotice(false);
        pointHistory.setTask(String.format("missionComplete {}",jaraUsId));
        pointHistory.setUser(userRepository.findByUserId(userId));

        pointHistoryRepository.save(pointHistory);
        updatedPoint = pointRepository.plusPoint(userId, changeAmount);

        // 알림 메시지 생성 및 발송
        String notificationMessage = String.format("자라어스 미션 완료로 %d포인트가 적립되었습니다.", changeAmount);

        return updatedPoint;
    }
}
