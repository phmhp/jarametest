package SiliconDream.JaraMe.dto;

import java.util.List;

public class UserDto {
    private Long userid;
    private String nickname;
    private String password;
    private String confirmPassword;
    private String email;
    private String profileImage;
    private String interest;

    // Constructors, getters, and setters

    public UserDto() {
    }

    public UserDto(Long userid, String nickname, String password, String confirmPassword, String email, String interest) {

        this.userid = userid;
        this.nickname = nickname;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.interest = interest;

    }

    // Getters and setters

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
    @Override
    public String toString() {
        return "UserDto{" +
                    "userid=" + userid +
                    ", nickname='" + nickname + '\'' +
                    ", password='" + password + '\'' +
                    ", confirmPassword='" + confirmPassword + '\'' +
                    ", email='" + email + '\'' +
                    ", interest='" + interest + '\'' +
                    '}';
        }

}