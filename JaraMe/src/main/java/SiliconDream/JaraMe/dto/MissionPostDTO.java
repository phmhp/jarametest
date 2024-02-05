package SiliconDream.JaraMe.dto;

import java.awt.*;
import java.time.LocalDateTime;

public class MissionPostDTO {
    /*//작성자 알기 위해 userId 필요(프로필)
    Long userId;
*/

    //어떤 미션인지 알기 위해서 jaraUsId 필요
    Long jaraUsId;

    //인증글 작성 시간
    LocalDateTime postDateTime;


    //전체 공개 여부
    boolean display;

    //익명 여부 => TODO: 프론트엔드 프로토타입 기반 => 상의 필요
    boolean anonymous;

    //인증 내용
    String textTitle;
    String textContent;
    String imageContent;
    private Long missionPostId;
    private String profileImage;
    private String nickname;


    //TODO:Getter and Setter
    /*
    public Long getUserId() {
        return userId;
    }*/


    public Long getJaraUsId() {
        return jaraUsId;
    }

    public LocalDateTime getPostDateTime() {
        return postDateTime;
    }

    public String getTextTitle() {
        return textTitle;

    }

    public String getTextContent() {
        return textContent;
    }

    public String getImageContent() {
        return imageContent;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }


    public void setMissionPostId(Long missionPostId) {
        this.missionPostId = missionPostId;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public void setPostDateTime(LocalDateTime postDateTime) {
        this.postDateTime = postDateTime;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
