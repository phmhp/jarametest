package SiliconDream.JaraMe.dto;

public class ReactionDTO {

    public ReactionDTO(Long reactionId,
                       String reactionType,
                       String nickname,
                       String profileImage) {
        this.reactionId = reactionId;
        this.reactionType = reactionType;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
    private Long reactionId;

    private String reactionType;


    private String nickname;

    private String profileImage;


}
