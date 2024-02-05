package SiliconDream.JaraMe.dto;

public class MissionReactionDTO {

    private Long missionPostId;

    public Long getMissionPostId() {
        return missionPostId;
    }

    public void setMissionPostId(Long missionPostId) {
        this.missionPostId = missionPostId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    private String reactionType;
}
