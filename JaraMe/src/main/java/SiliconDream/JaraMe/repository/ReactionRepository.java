package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import SiliconDream.JaraMe.domain.Comment;
import SiliconDream.JaraMe.domain.Reaction;
import SiliconDream.JaraMe.dto.ReactionCountDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Long> {
    Optional<Reaction> findByReactionId(Long reactionId);

    Optional<Reaction> findReactionByMissionPost_MissionPostIdAndUser_UserId(Long missionPostId, Long userId);


    //미션 인증글 조회 = 리액션 안단 경우에는 => optional을 통해 null
    @Query("SELECT r.reactionType FROM Reaction r WHERE r.missionPost.missionPostId = :missionPostId AND r.user.userId = :userId")
    Optional<String> findReactionTypeByMissionPost_MissionPostIdAndUser_UserId(Long missionPostId, Long userId);


    @Query("SELECT r.reactionType, COUNT(*) as count " +
            "FROM Reaction r " +
            "LEFT JOIN r.missionPost as rmp " +
            "WHERE rmp.missionPostId = :missionPostId " +
            "GROUP BY r.reactionType")
    Optional<List<Object[]>> findByMissionPost_MissionPostId(@RequestParam Long missionPostId);

    @Query("SELECT r " +
            "FROM Reaction r " +
            "LEFT JOIN r.missionPost as rmp " +
            "WHERE rmp.missionPostId = :missionPostId")
    List<Reaction> findReactionByMissionPost_MissionPostId(@RequestParam Long missionPostId);

    @Modifying
    @Query("UPDATE Reaction r " +
            "SET r.notice = :notice " +
            "WHERE r.missionPost.missionPostId = :missionPostId ")
    default void updateNoticeByMissionPost_MissionPostId(@RequestParam Long missionPostId,boolean notice) {
        List<Reaction> reactionList = findReactionByMissionPost_MissionPostId(missionPostId);
        for (Reaction one : reactionList) {
            one.setNotice(notice);
            save(one);
        }

    }

}
