package SiliconDream.JaraMe.dto;

public class ReactionNoticeDTO {


    public ReactionNoticeDTO(Long missionPostId,String missionPostTextTitle, int like, int good, int smile) {
        this.missionPostId = missionPostId;
        this.missionPostTextTitle = missionPostTextTitle;

        this.like = like;
        this.good = good;
        this.smile = smile;
    }


    private Long missionPostId;
    private String missionPostTextTitle;
    private int like;

    private int good;
    private int smile;


    public void setLike(int like) {
        this.like = like;
    }

    public void setSmile(int smile) {
        this.smile = smile;
    }

    public void setGood(int good) {
        this.good = good;
    }


    public int getLike() {
        return like;
    }

    public int getSmile() {
        return smile;
    }

    public int getGood() {
        return good;
    }

    public Long getMissionPostId() {
        return missionPostId;
    }

    public void setMissionPostId(Long missionPostId) {
        this.missionPostId = missionPostId;
    }

    public String getMissionPostTextTitle() {
        return missionPostTextTitle;
    }

    public void setMissionPostTextTitle(String missionPostTextTitle) {
        this.missionPostTextTitle = missionPostTextTitle;
    }

}
