package SiliconDream.JaraMe.dto;

public class UserProfileInfoDTO {
    private Long userId;
    private String nickname;
    private String profileImage;
    private int points;
    private int passTicket;
    private int participatingJaraUsCount;

    //Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getParticipatingJaraUsCount() {
        return participatingJaraUsCount;
    }

    public void setParticipatingJaraUsCount(int participatingJaraUsCount) {
        this.participatingJaraUsCount = participatingJaraUsCount;
    }

    public int getPassTicket() {
        return passTicket;
    }

    public void setPassTicket(int passTicket) {
        this.passTicket = passTicket;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
