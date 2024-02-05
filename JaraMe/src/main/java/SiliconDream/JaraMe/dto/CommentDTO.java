package SiliconDream.JaraMe.dto;

import java.time.LocalDateTime;

public class CommentDTO {

    public CommentDTO(Long commentId,
                      String commentContent,
                      LocalDateTime commentDate,
                      String nickname
            , String profileImage) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
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


    private Long commentId;

    private String commentContent;

    private LocalDateTime commentDate;

    private String nickname;

    private String profileImage;



}
