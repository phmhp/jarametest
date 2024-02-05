package SiliconDream.JaraMe.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import SiliconDream.JaraMe.domain.MissionHistory;
import SiliconDream.JaraMe.dto.DailyMissionRecordDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MissionHistoryRepository extends JpaRepository<MissionHistory, Long> {

    @Query("SELECT m.missionDate " +
            "FROM MissionHistory m " +
            "LEFT JOIN m.jaraUs mj " +
            "LEFT JOIN m.user mu " +
            "WHERE mu.userId = :userId AND mj.jaraUsId = :jaraUsId AND m.missionResult = :missionResult")
    Set<LocalDate> findMissionDateByUser_UserIdAndJaraUs_JaraUsIdAndMissionResult(Long userId, Long jaraUsId, boolean missionResult);

    default void saveDailyMissionRecord(DailyMissionRecordDTO dailyMissionRecordDTO) {

        MissionHistory missionHistory = new MissionHistory();
        missionHistory.setMissionDate(dailyMissionRecordDTO.getMissionDate());
        missionHistory.setJaraUs(dailyMissionRecordDTO.getJaraUs());
        missionHistory.setUser(dailyMissionRecordDTO.getUser());
        missionHistory.setMissionPost(dailyMissionRecordDTO.getMissionPost());
        missionHistory.setMissionResult(dailyMissionRecordDTO.isMissionResult());
        save(missionHistory);
    }

    List<Long> findMissionPostIdsByUser_UserId(Long userId);


    @Query("SELECT mh.missionDate, COUNT(*) as count " +
            "FROM MissionHistory mh " +
            "LEFT JOIN mh.user mhu " +
            "WHERE mhu.userId = :userId AND mh.missionResult = :missionResult " +
            "GROUP BY mh.missionDate")
    Optional<List<Object[]>> findMissionDateByUser_UserIdAndMissionResult(Long userId, boolean missionResult);

    @Query("SELECT mh FROM MissionHistory mh " +
            "LEFT JOIN mh.user mhu " +
            "WHERE mhu.userId = :userId AND mh.missionDate = :missionDate")
    List<MissionHistory> findByUser_UserIdAndMissionDate(@Param("userId") Long userId, @Param("missionDate") LocalDate missionDate);

    @Transactional
    @Modifying
    @Query("UPDATE MissionHistory mh " +
            "SET mh.missionResult = :missionResult " +
            "WHERE mh.user.userId = :userId AND mh.missionDate = :missionDate")
    void updateMissionResultByUser_UserIdAndMissionDate(@Param("userId") Long userId,
                                                        @Param("missionDate") LocalDate missionDate,
                                                        @Param("missionResult") boolean missionResult);



    @Query("SELECT MissionHistory mh " +
            "FROM MissionHistory mh " +
            "LEFT JOIN mh.missionPost mhm " +
            "WHERE mhm.missionPostId = :missionPostId")
    MissionHistory findMissionHistoryIdByMissionPost_MissionPostId(Long missionPostId);



    @Modifying
    @Transactional
    @Query("UPDATE MissionHistory mh SET mh.missionPost = null WHERE mh.missionPost.missionPostId = :missionPostId")

    void updateMissionPostToNull(@Param("missionPostId") Long missionPostId);
}

