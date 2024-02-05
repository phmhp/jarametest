package SiliconDream.JaraMe.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class GetMissionPostDTO {

/*
    public GetMissionPostDTO(Long missionPostId,
                             String textTitle,
                             String textContent,
                             String imageContent,
                             LocalDateTime postDateTime,
                             String nickname,
                             String profileImage,
                             List<CommentDTO> CommentDTO,
                             List<ReactionDTO> ReactionDTO) {
        this.missionPostId = missionPostId;
        this.textTitle = textTitle;
        this.textContent = textContent;
        this.imageContent = imageContent;
        this.postDateTime = postDateTime;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.CommentDTO = CommentDTO != null ? CommentDTO : Collections.emptyList();;
        this.ReactionDTO = ReactionDTO != null ? ReactionDTO : Collections.emptyList();;

    }*/

    //TODO: 인증글
    private Long missionPostId;

    private String textTitle;

    private String textContent;

    private String imageContent;    //인증글 이미지 파일 => 몇장까지 첨부할지에 따라 구조가 달라질 수도 있음.

    private LocalDateTime postDateTime;

    private boolean display;
    private boolean anonymous;

    private String nickname;

    private String profileImage;

    //TODO: 댓글
    private List<CommentDTO> CommentDTO;

    //TODO: 리액션
    private String reactionType; //수정 (조회 요청한 유저가 눌럿던 리액션만 반환 )
/*
    //TODO: getter and setter

    public Long getMissionPostId() {
        return missionPostId;
    }

    public void setMissionPostId(Long missionPostId) {
        this.missionPostId = missionPostId;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public LocalDateTime getPostDateTime() {
        return postDateTime;
    }

    public void setPostDateTime(LocalDateTime postDateTime) {
        this.postDateTime = postDateTime;
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

    public List<siliconDream.jaraMe.dto.CommentDTO> getCommentDTO() {
        return CommentDTO;
    }

    public void setCommentDTO(List<siliconDream.jaraMe.dto.CommentDTO> commentDTO) {
        CommentDTO = commentDTO;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }


//        private Long reactionLike=0L;
//        private Long reactionThumb=0L;
//        private Long reactionSmile=0L;



*/












}

