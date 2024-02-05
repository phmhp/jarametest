package SiliconDream.JaraMe.dto;

import siliconDream.jaraMe.domain.User;

public class LoginResponse {
    private boolean success;
    private String errorCode;
    private User user; // 성공 시 사용자 정보 포함

    // 기본 생성자, getter, setter 메소드 추가
    public LoginResponse() {}

    public LoginResponse(boolean success, String errorCode, User user) {
        this.success = success;
        this.errorCode = errorCode;
        this.user = user;
    }

    // Getter와 Setter 메소드
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
