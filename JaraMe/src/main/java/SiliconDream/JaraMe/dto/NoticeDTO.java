package SiliconDream.JaraMe.dto;

import java.util.List;
import java.util.Optional;

public class NoticeDTO {

    //미션완주 //TODO: 포인트 이력 나타내는 엔티티에 포인트 지급 공지 전달 유무 나타내는 필드 추가하기!
    private Optional<List<MissionCompleteNoticeDTO>> missionCompleteNoticeDTO;



    //리액션 통계
    //로그인한 유저가 작성한 미션 인증글 중 리액션이 업데이트되었다면 => #TODO: 리액션 엔티티 통계 공지 전달 유무 나타내는 필드 추가하기!
    private Optional<List<ReactionNoticeDTO>> reactionNoticeDTO;



    public Optional<List<MissionCompleteNoticeDTO>> getMissionCompleteNoticeDTO() {
        return missionCompleteNoticeDTO;
    }

    public void setMissionCompleteNoticeDTO(Optional<List<MissionCompleteNoticeDTO>> missionCompleteNoticeDTO) {
        this.missionCompleteNoticeDTO = missionCompleteNoticeDTO;
    }

    public Optional<List<ReactionNoticeDTO>> getReactionNoticeDTO() {
        return reactionNoticeDTO;
    }

    public void setReactionNoticeDTO(Optional<List<ReactionNoticeDTO>> reactionNoticeDTO) {
        this.reactionNoticeDTO = reactionNoticeDTO;
    }

}
