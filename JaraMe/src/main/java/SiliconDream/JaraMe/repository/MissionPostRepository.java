package SiliconDream.JaraMe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import SiliconDream.JaraMe.domain.MissionPost;
import SiliconDream.JaraMe.dto.GetMissionPostDTO;
import SiliconDream.JaraMe.dto.MissionPostDTO;

import java.util.List;

import java.util.Optional;

@Repository
public interface MissionPostRepository extends JpaRepository<MissionPost, Long> {

    MissionPost findByMissionPostId(Long missionPostId);

    MissionPost save(MissionPost missionPost);


    //미션 인증글 조회

    Optional<MissionPost> findMissionPostByMissionPostId(@Param("missionPostId") Long missionPostId);




    @Modifying
    @Query("UPDATE MissionPost mp SET mp.display = :display, mp.anonymous = :anonymous, mp.textTitle = :textTitle, mp.textContent = :textContent, mp.imageContent =:imageContent " +
            "WHERE mp.missionPostId = :missionPostId")
    default void updateMissionPostByMissionPostId(Long missionPostId, boolean display, boolean anonymous, String textTitle, String textContent, String imageContent){
        MissionPost missionPost = findByMissionPostId(missionPostId);
        missionPost.setDisplay(display);
        missionPost.setAnonymous(anonymous);
        missionPost.setTextTitle(textTitle);
        missionPost.setTextContent(textContent);
        missionPost.setImageContent(imageContent);
        save(missionPost);
    }


    @Query("SELECT mp.missionPostId " +
            "FROM MissionPost mp " +
            "LEFT JOIN mp.user as mpu " +
            "WHERE mpu.userId = :userId")
    Optional<List<Long>> findMissionPostIdByUser_UserId(Long userId);

    List<MissionPost> findAllByJaraUs_JaraUsIdAndUser_UserId(Long jaraUsId, Long userId);

    List<MissionPost> findByJaraUs_JaraUsId(Long jaraUsId);

/* 
    void delete(MissionPost missionPost);*/

}
