package SiliconDream.JaraMe.dto;

import java.time.LocalDateTime;

public class MissionCommentDTO {
    //댓글 등록 시 전달받아야하는 값들


    //댓글작성자 정보 => 세션으로 전달
    //게시글 정보
    private Long missionPostId;
    //댓글내용
    private String commentContent;

    //댓글작성 일시
    private LocalDateTime commentDateTime;




    public Long getMissionPostId() {
        return missionPostId;
    }

    public void setMissionPostId(Long missionPostId) {
        this.missionPostId = missionPostId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public LocalDateTime getCommentDateTime() {
        return commentDateTime;
    }

    public void setCommentDateTime(LocalDateTime commentDateTime) {
        this.commentDateTime = commentDateTime;
    }






}
