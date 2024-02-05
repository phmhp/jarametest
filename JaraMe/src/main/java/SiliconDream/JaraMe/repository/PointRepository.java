package SiliconDream.JaraMe.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import siliconDream.jaraMe.domain.User;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<User, Long> {


    // userId로 조회
    Optional<User> findByUserId(Long userId);

    //출석체크 (포인트 적립)
    default void updateCheckIn(Long userId) {

        try {
            Optional<User> userOptional = findByUserId(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPoint(user.getPoint() + 2);
                user.setCheckIn(true);
                save(user);

            }

        } catch (EntityNotFoundException e) {
            e.printStackTrace();

        }

    }




    // 패스권 구매 (포인트 차감)
    default boolean updatePassTicket(Long userId) {
        try {
            Optional<User> userOptional = findByUserId(userId);
            User user = userOptional.get();
            if (user != null) {
                user.setPoint(user.getPoint() - 60);
                user.setPassTicket((user.getPassTicket() + 1));
                save(user);
                return true;
            } else {
                return false;
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }




    //TODO: 오늘의 미션 완료 (포인트 적립)
    default boolean updateDailyMission(Long userId, int earnedPoint) {
        try {
            Optional<User> userOptional = findByUserId(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPoint(user.getPoint() + earnedPoint);
                //오늘의 미션 기록 / 미션 히스토리에 업데이트?
                save(user);
                return true;
            } else {
                return false;
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    //TODO: 미션 완주
    default int plusPoint(Long userId, int changeAmount) {
        try {
            Optional<User> userOptional = findByUserId(userId);
            User user = userOptional.get();
            if (user != null) {
                user.setPoint(user.getPoint() + changeAmount);
                save(user);
                return user.getPoint();
            }
        } catch (
                EntityNotFoundException e) {
            e.printStackTrace();

        }
        return 0; //수정하기(제대로 로직 수행 못했을 경우 반환값)
    }
}