package SiliconDream.JaraMe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MissionPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long missionPostId;

    private boolean anonymous;

    private boolean display;

    private String textTitle;

    private String textContent;

    private String imageContent;


    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postDateTime;


    //FK
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "jaraUs")
    private JaraUs jaraUs;


    @OneToOne
    @JoinColumn(name = "missionHistory")
    @JsonIgnore
    private MissionHistory missionHistory;

    @OneToMany(mappedBy = "missionPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comment;

    @OneToMany(mappedBy = "missionPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reaction> reaction;


    //TODO: getter and setter

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

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Reaction> getReaction() {
        return reaction;
    }

    public void setReaction(List<Reaction> reaction) {
        this.reaction = reaction;
    }

    public Long getMissionPostId() {
        return missionPostId;
    }

    public void setMissionPostId(Long missionPostId) {
        this.missionPostId = missionPostId;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JaraUs getJaraUs() {
        return jaraUs;
    }

    public void setJaraUs(JaraUs jaraUs) {
        this.jaraUs = jaraUs;
    }

}
