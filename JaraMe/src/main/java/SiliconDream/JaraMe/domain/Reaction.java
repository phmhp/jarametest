package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;

@Entity

@Table
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,unique = true)
    private Long reactionId;
    private String reactionType; //like, smile, good


    @Column(columnDefinition = "boolean default false")
    private boolean notice;//erd다이어그림에 업데이트하기

    //FK
    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name="missionPost")
    private MissionPost missionPost;

    @ManyToOne
    @JoinColumn(name="user")
    private User user;


    //TODO: getter and setter
    public Long getReactionId() {
        return reactionId;
    }
    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public String getReactionType() {
        return reactionType;
    }
    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }
    public MissionPost getMissionPost() {
        return missionPost;
    }

    public void setMissionPost(MissionPost missionPost) {
        this.missionPost = missionPost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean isNotice() {
        return notice;
    }

    public void setNotice(boolean notice) {
        this.notice = notice;
    }


}
