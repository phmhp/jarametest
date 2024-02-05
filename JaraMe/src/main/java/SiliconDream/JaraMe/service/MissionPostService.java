package SiliconDream.JaraMe.service;

import SiliconDream.JaraMe.domain.MissionPost;
import SiliconDream.JaraMe.dto.CommentDTO;

import SiliconDream.JaraMe.dto.DailyMissionDTO;
import SiliconDream.JaraMe.dto.MissionPostDTO;
import SiliconDream.JaraMe.dto.GetMissionPostDTO;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;


public interface MissionPostService {
    //미션 인증글 작성
    boolean missionPost(MissionPostDTO missionPostDTO, Long userId);

    //미션 인증글 조회
    GetMissionPostDTO getMissionPostDetails(Long missionPostId,Long userId);
    GetMissionPostDTO makeGetMissionPostDTO(Optional<MissionPost> missionPostOptional, Optional<String> reactionTypeOptional, Optional<List<CommentDTO>> commentOptional);
    boolean existMissionPost(Long missionPostId);


    void dailyMissionUpdate(Long userId,Long jaraUsId, Long missionPostId);
    void  dailyMissionFinish(Long userId);
    boolean compareMissionPost(MissionPostDTO missionPostDTO, Long missionPostId);

    //Optional<DailyMissionDTO> getDailyMission(Long userId, LocalDateTime todayDate);
    int missionParticipationRate(Long userId, Long jaraUsId);

    String updateMissionPost(Long missionPostId, MissionPostDTO missionPostDTO, Long userId, LocalDate todayDate);

    List<MissionPostDTO> getAllMissionPosts(Long jaraUsId);

    List<MissionPostDTO> getMyMissionPostsForJaraUs(Long jaraUsId, Long userId);


    //String deleteMissionPost(Long missionPostId,Long userId); 보류

    }
