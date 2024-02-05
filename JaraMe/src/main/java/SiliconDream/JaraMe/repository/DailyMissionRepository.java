package SiliconDream.JaraMe.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import SiliconDream.JaraMe.domain.DailyMission;
import SiliconDream.JaraMe.domain.MissionHistory;
import SiliconDream.JaraMe.domain.MissionPost;
import SiliconDream.JaraMe.dto.DailyMissionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface DailyMissionRepository extends JpaRepository<DailyMission, Long> {

    @PersistenceContext


        //userId로 오늘의 미션 조회
    List<DailyMission> findByUser_UserId(Long userId);

    //오늘의 미션(개별 미션) 진행상황 업데이트
    //TODO:missionPostId 추가 및 done_date_time 추가 및 dailyMissionResult 변경

    @Query("SELECT dm.dailyMissionId FROM DailyMission dm WHERE dm.user.userId = :userId AND dm.jaraUs.jaraUsId = :jaraUsId")
    Long findDailyMissionIdByUser_UserIdAndJaraUs_JaraUsId(Long userId, Long jaraUsId);

    DailyMission findByDailyMissionId(Long dailyMissionId);


    default void updateDailyMissionStatus(boolean status, Long dailyMissionId, MissionPost savedMissionPost, LocalDateTime postedDateTime) {
        DailyMission dailyMission = findByDailyMissionId(dailyMissionId);
        dailyMission.setDailyMissionResult(true);
        dailyMission.setMissionPost(savedMissionPost);
        dailyMission.setDoneDateTime(postedDateTime);
        save(dailyMission);
    }

    DailyMission findDailyMissionByDailyMissionId(Long dailyMissionId);

    @Transactional
    void deleteByUserUserId(Long userId);

    @Query("SELECT new siliconDream.jaraMe.dto.DailyMissionDTO(dm.dailyMissionResult, dmg.jaraUsId, dmj.jaraUsName, dmj.missionName) " +
            "FROM DailyMission dm LEFT JOIN dm.jaraUs dmj WHERE dm.user.userId = :userId AND dm.scheduleDate = :scheduleDate")
    List<DailyMissionDTO> findDailyMissionDTOByScheduleDateAndUser_UserId( LocalDate scheduleDate,Long userId);


    //dailyMission테이블에 있는지 찾아보기 => dailyMissionResult==true면 가장 최근 게시글이 MissionPostId와 동일하지도 체크 ? 아니면 DailyMission,MissionHistory테이블에 미션포스트넘버 추가?
    @Query("SELECT dm.missionPost.missionPostId " +
            "FROM DailyMission dm " +
            "WHERE dm.user.userId= :userId AND dm.scheduleDate = :todayDate AND dm.dailyMissionResult = :dailyMissionResult")
    Optional<Long> findMissionPostIdByUser_UserIdAndScheduleDateAndDailyMissionResult(Long userId, LocalDate todayDate, boolean dailyMissionResult);


    List<Long> findMissionPostIdsByUser_UserId(Long userId);

    @Query("SELECT DailyMission dm " +
            "FROM DailyMission dm " +
            "LEFT JOIN dm.missionPost dmm " +
            "WHERE dmm.missionPostId = :missionPostId")
    DailyMission findDailyMissionByMissionPost_MissionPostId(Long missionPostId);

}
