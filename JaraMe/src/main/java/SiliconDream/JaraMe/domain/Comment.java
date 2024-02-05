package SiliconDream.JaraMe.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity

@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long commentId;

    private String commentContent;

    private LocalDateTime commentDate;



    // FK
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "missionPost")
    private MissionPost missionPost;

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "user")
    private User user;







    //TODO: getter and setter

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



}